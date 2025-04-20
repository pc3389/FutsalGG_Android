package com.futsalgg.app.presentation.user.util

import android.util.Log
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.user.repository.UserRepository
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.common.error.toUiError
import com.futsalgg.app.presentation.common.state.EditTextState
import com.futsalgg.app.presentation.common.state.UiState

val slangList: List<String> = listOf(
    "시발", "씨발", "병신", "새끼", "개새끼", "미친놈", "지랄", "좆밥", "니애미", "느금마", "창녀", "매춘부"
)

class NicknameChecker(
    private val userRepository: UserRepository
) {
    suspend fun checkNickname(
        nickname: String,
        onStateUpdate: (EditTextState) -> Unit,
        onUiStateUpdate: (UiState) -> Unit
    ) {
        if (!nickname.matches(Regex("^[a-zA-Z가-힣0-9]+$"))) {
            onStateUpdate(EditTextState.ErrorCannotUseSpecialChar)
            onUiStateUpdate(UiState.Initial)
            return
        }
        if (slangList.any {nickname.contains(it)}) {
            onStateUpdate(EditTextState.ErrorCannotUseSlang)
            onUiStateUpdate(UiState.Initial)
        }

        try {
            val result = userRepository.isNicknameUnique(nickname)
            result.fold(
                onSuccess = { isUnique ->
                    onStateUpdate(
                        if (isUnique) EditTextState.Available
                        else EditTextState.ErrorAlreadyExisting
                    )
                    onUiStateUpdate(UiState.Success)
                },
                onFailure = { error ->
                    Log.e(
                        "NicknameChecker",
                        "Nickname check 에러: ${error.message}",
                        error
                    )
                    onUiStateUpdate(
                        UiState.Error(
                            (error as? DomainError)?.toUiError()
                                ?: UiError.UnknownError("알 수 없는 오류가 발생했습니다.")
                        )
                    )
                    onStateUpdate(EditTextState.ErrorAlreadyExisting)
                }
            )
        } catch (e: Exception) {
            Log.e("NicknameChecker", "Exception during nickname check", e)
            onUiStateUpdate(UiState.Error(UiError.UnknownError("알 수 없는 오류가 발생했습니다.")))
            onStateUpdate(EditTextState.ErrorAlreadyExisting)
        }
    }
}