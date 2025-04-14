package com.futsalgg.app.presentation.user.util

import android.util.Log
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.user.repository.UserRepository
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.common.error.toUiError
import com.futsalgg.app.presentation.common.state.EditTextState
import com.futsalgg.app.presentation.common.state.UiState

class NicknameChecker(
    private val userRepository: UserRepository
) {
    suspend fun checkNickname(
        nickname: String,
        onStateUpdate: (EditTextState) -> Unit,
        onUiStateUpdate: (UiState) -> Unit
    ) {
        if (!nickname.matches(Regex("^[ㄱ-힣]+$"))) {
            onStateUpdate(EditTextState.ErrorCannotUse)
            onUiStateUpdate(UiState.Initial)
            return
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