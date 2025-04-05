package com.futsalgg.app.presentation.team.createteam

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.team.usecase.CreateTeamUseCase
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.common.error.toUiError
import com.futsalgg.app.presentation.common.state.EditTextState
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.presentation.team.model.Access
import com.futsalgg.app.presentation.team.model.MatchType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CreateTeamViewModel @Inject constructor(
    private val createTeamUseCase: CreateTeamUseCase,
    private val tokenManager: ITokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _createTeamState = MutableStateFlow(CreateTeamState())
    val createTeamState: StateFlow<CreateTeamState> = _createTeamState.asStateFlow()

    internal fun onTeamNameChange(newValue: String) {
        _createTeamState.value = _createTeamState.value.copy(
            teamName = newValue,
            teamNameState = EditTextState.Initial
        )
    }

    internal fun onIntroductionChange(newValue: String) {
        _createTeamState.value = _createTeamState.value.copy(
            introduction = newValue
        )
    }

    internal fun onRuleChange(newValue: String) {
        _createTeamState.value = _createTeamState.value.copy(
            rule = newValue
        )
    }

    internal fun onMatchTypeChange(matchType: MatchType) {
        _createTeamState.value = _createTeamState.value.copy(
            matchType = matchType
        )
    }

    internal fun onAccessChange(access: Access) {
        _createTeamState.value = _createTeamState.value.copy(
            access = access
        )
    }

    internal fun onDuesChange(newValue: String) {
        _createTeamState.value = _createTeamState.value.copy(
            dues = newValue
        )
    }

    internal fun checkTeamNameDuplication() {
        val currentTeamName = _createTeamState.value.teamName

        if (currentTeamName.isEmpty()) {
            _createTeamState.value = _createTeamState.value.copy(
                teamNameState = EditTextState.Initial
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            
            try {
                val result = createTeamUseCase.isTeamNicknameUnique(currentTeamName)
                result.fold(
                    onSuccess = { isUnique ->
                        _createTeamState.value = _createTeamState.value.copy(
                            teamNameState = if (isUnique) {
                                EditTextState.Available
                            } else {
                                EditTextState.ErrorAlreadyExisting
                            }
                        )
                        _uiState.value = UiState.Success
                    },
                    onFailure = { error ->
                        Log.e(
                            "CreateTeamViewModel",
                            "Team name check 에러: ${error.message}",
                            error
                        )
                        _uiState.value = UiState.Error(
                            (error as? DomainError)?.toUiError()
                                ?: UiError.UnknownError("알 수 없는 오류가 발생했습니다.")
                        )
                        _createTeamState.value = _createTeamState.value.copy(
                            teamNameState = EditTextState.ErrorAlreadyExisting
                        )
                    }
                )
            } catch (e: Exception) {
                Log.e("CreateTeamViewModel", "Exception during team name check", e)
                _uiState.value = UiState.Error(UiError.UnknownError("알 수 없는 오류가 발생했습니다."))
                _createTeamState.value = _createTeamState.value.copy(
                    teamNameState = EditTextState.ErrorAlreadyExisting
                )
            }
        }
    }

    internal fun createTeam(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            
            try {
                val accessToken = tokenManager.getAccessToken()

                if (accessToken.isNullOrEmpty()) {
                    Log.e("CreateTeamViewModel", "엑세스 토큰이 존재하지 않습니다")
                    _uiState.value = UiState.Error(UiError.AuthError("엑세스 토큰이 존재하지 않습니다"))
                    return@launch
                }

                val result = createTeamUseCase.createTeam(
                    accessToken = accessToken,
                    name = _createTeamState.value.teamName,
                    introduction = _createTeamState.value.introduction,
                    rule = _createTeamState.value.rule,
                    matchType = MatchType.toDomain(_createTeamState.value.matchType),
                    access = Access.toDomain(_createTeamState.value.access),
                    dues = _createTeamState.value.dues.toIntOrNull() ?: 0
                )
                result.fold(
                    onSuccess = {
                        _uiState.value = UiState.Success
                        onSuccess()
                    },
                    onFailure = { error ->
                        Log.e(
                            "CreateTeamViewModel",
                            "Create team 에러: ${error.message}",
                            error
                        )
                        _uiState.value = UiState.Error(
                            (error as? DomainError)?.toUiError()
                                ?: UiError.UnknownError("알 수 없는 오류가 발생했습니다.")
                        )
                        _createTeamState.value = _createTeamState.value.copy(
                            errorMessage = error.message
                        )
                    }
                )
            } catch (e: Exception) {
                Log.e("CreateTeamViewModel", "Exception during team creation", e)
                _uiState.value = UiState.Error(UiError.UnknownError("알 수 없는 오류가 발생했습니다."))
                _createTeamState.value = _createTeamState.value.copy(
                    errorMessage = e.message
                )
            }
        }
    }

    fun uploadTeamImage(file: File) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val accessToken = tokenManager.getAccessToken()
            if (accessToken.isNullOrEmpty()) {
                logError("AccessToken이 존재하지 않습니다.")
                _uiState.value = UiState.Error(UiError.AuthError("AccessToken이 존재하지 않습니다"))
                return@launch
            }

            val result = createTeamUseCase.updateTeamLogo(
                accessToken,
                // TODO Team ID and Uri
                teamId = "",
                uri = ""
            )
            result.fold(
                onSuccess = { response ->
                    _createTeamState.value = _createTeamState.value.copy(
                        teamImageUrl = response.url
                    )
                    _uiState.value = UiState.Success
                },
                onFailure = { throwable ->
                    logError("팀 이미지 업로드 오류: ${throwable.message}")
                    _uiState.value = UiState.Error(
                        (throwable as? DomainError)?.toUiError()
                            ?: UiError.UnknownError("알 수 없는 오류가 발생했습니다.")
                    )
                    _createTeamState.value = _createTeamState.value.copy(
                        errorMessage = throwable.message
                    )
                }
            )
        }
    }

    internal fun setCroppedImage(bitmap: Bitmap) {
        _createTeamState.value = _createTeamState.value.copy(
            croppedTeamImage = bitmap
        )
    }

    private fun logError(message: String) {
        Log.e("CreateTeamViewModel", message)
    }
} 