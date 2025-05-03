package com.futsalgg.app.presentation.team.createorupdateteam

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.team.usecase.CheckTeamNicknameUniqueUseCase
import com.futsalgg.app.domain.team.usecase.CreateTeamUseCase
import com.futsalgg.app.domain.team.usecase.UpdateTeamLogoUseCase
import com.futsalgg.app.domain.team.usecase.UpdateTeamUseCase
import com.futsalgg.app.presentation.common.SharedViewModel
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.common.error.toUiError
import com.futsalgg.app.presentation.common.model.MatchType
import com.futsalgg.app.presentation.common.state.EditTextState
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.presentation.team.model.Access
import com.futsalgg.app.presentation.user.util.slangList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
open class ModifyTeamViewModel @Inject constructor(
    private val checkTeamNicknameUniqueUseCase: CheckTeamNicknameUniqueUseCase,
    private val updateTeamLogoUseCase: UpdateTeamLogoUseCase,
    private val updateTeamUseCase: UpdateTeamUseCase,
    private val sharedViewModel: SharedViewModel,
    private val createTeamUseCase: CreateTeamUseCase,
    private val tokenManager: ITokenManager
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _modifyTeamState = MutableStateFlow(ModifyTeamState())
    val modifyTeamState: StateFlow<ModifyTeamState> = _modifyTeamState.asStateFlow()

    protected val accessToken by lazy {
        val token = tokenManager.getAccessToken()

        if (token.isNullOrEmpty()) {
            Log.e("CreateTeamViewModel", "엑세스 토큰이 존재하지 않습니다")
            _uiState.value = UiState.Error(UiError.AuthError("엑세스 토큰이 존재하지 않습니다"))
        }

        token ?: ""
    }

    init {
        sharedViewModel.teamState.value?.let {
            _modifyTeamState.value = _modifyTeamState.value.copy(
                teamName = it.name,
                teamNameState = EditTextState.Available,
                access = when (it.access.name) {
                    Access.TEAM_LEADER.name -> Access.TEAM_LEADER
                    Access.TEAM_DEPUTY_LEADER.name -> Access.TEAM_DEPUTY_LEADER
                    else -> Access.TEAM_SECRETARY
                },
                introduction = it.introduction,
                rule = it.rule,
                dues = "0",
                teamImageUrl = it.logoUrl
            )
        }
    }

    internal fun onTeamNameChange(newValue: String) {
        _modifyTeamState.value = _modifyTeamState.value.copy(
            teamName = newValue,
            teamNameState = EditTextState.Initial
        )
    }

    internal fun onIntroductionChange(newValue: String) {
        _modifyTeamState.value = _modifyTeamState.value.copy(
            introduction = newValue
        )
    }

    internal fun onRuleChange(newValue: String) {
        _modifyTeamState.value = _modifyTeamState.value.copy(
            rule = newValue
        )
    }

    internal fun onMatchTypeChange(matchType: MatchType) {
        _modifyTeamState.value = _modifyTeamState.value.copy(
            matchType = matchType
        )
    }

    internal fun onAccessChange(access: Access) {
        _modifyTeamState.value = _modifyTeamState.value.copy(
            access = access
        )
    }

    internal fun onDuesChange(newValue: String) {
        _modifyTeamState.value = _modifyTeamState.value.copy(
            dues = newValue
        )
    }

    internal fun checkTeamNameDuplication() {
        val currentTeamName = _modifyTeamState.value.teamName

        if (!sharedViewModel.teamState.value?.name.isNullOrEmpty()
            && currentTeamName == sharedViewModel.teamState.value?.name
        ) {
            _modifyTeamState.value = _modifyTeamState.value.copy(
                teamNameState = EditTextState.Available
            )
            return
        }

        if (currentTeamName.isEmpty()) {
            _modifyTeamState.value = _modifyTeamState.value.copy(
                teamNameState = EditTextState.Initial
            )
            return
        }

        if (!currentTeamName.matches(Regex("^[a-zA-Z가-힣0-9\\s\\-_]+$"))) {
            _modifyTeamState.value = _modifyTeamState.value.copy(
                teamNameState = EditTextState.ErrorCannotUseSpecialChar
            )
            return
        }

        if (slangList.any { currentTeamName.contains(it) }) {
            _modifyTeamState.value = _modifyTeamState.value.copy(
                teamNameState = EditTextState.ErrorCannotUseSlang
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = UiState.Loading

            try {
                val result = checkTeamNicknameUniqueUseCase.invoke(currentTeamName)
                result.fold(
                    onSuccess = { isUnique ->
                        _modifyTeamState.value = _modifyTeamState.value.copy(
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
                        _modifyTeamState.value = _modifyTeamState.value.copy(
                            teamNameState = EditTextState.ErrorAlreadyExisting
                        )
                    }
                )
            } catch (e: Exception) {
                Log.e("CreateTeamViewModel", "Exception during team name check", e)
                _uiState.value = UiState.Error(UiError.UnknownError("알 수 없는 오류가 발생했습니다."))
                _modifyTeamState.value = _modifyTeamState.value.copy(
                    teamNameState = EditTextState.ErrorAlreadyExisting
                )
            }
        }
    }

    fun uploadTeamImage(file: File) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val result = withContext(Dispatchers.IO) {
                updateTeamLogoUseCase.invoke(
                    accessToken,
                    teamId = sharedViewModel.teamState.value?.id ?: "",
                    file
                )
            }
            result.fold(
                onSuccess = { response ->
                    _modifyTeamState.value = _modifyTeamState.value.copy(
                        teamImageUrl = response.url
                    )
                    _uiState.value = UiState.Success
                },
                onFailure = { throwable ->
                    _uiState.value = UiState.Error(
                        (throwable as? DomainError)?.toUiError()
                            ?: UiError.UnknownError("팀 이미지 업로드 오류: ${throwable.message}")
                    )
                    _modifyTeamState.value = _modifyTeamState.value.copy(
                        errorMessage = throwable.message
                    )
                }
            )
        }
    }

    internal fun setCroppedImage(bitmap: Bitmap) {
        _modifyTeamState.value = _modifyTeamState.value.copy(
            croppedTeamImage = bitmap,
            teamImageUrl = null
        )
    }


    internal fun createTeam(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            try {
                val result = createTeamUseCase.invoke(
                    accessToken = accessToken,
                    name = _modifyTeamState.value.teamName,
                    introduction = _modifyTeamState.value.introduction,
                    rule = _modifyTeamState.value.rule,
                    matchType = MatchType.toDomain(_modifyTeamState.value.matchType!!),
                    access = Access.toDomain(_modifyTeamState.value.access!!),
                    dues = _modifyTeamState.value.dues.toIntOrNull() ?: 0
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
                        _modifyTeamState.value = _modifyTeamState.value.copy(
                            errorMessage = error.message
                        )
                    }
                )
            } catch (e: Exception) {
                Log.e("CreateTeamViewModel", "Exception during team creation", e)
                _uiState.value = UiState.Error(UiError.UnknownError("알 수 없는 오류가 발생했습니다."))
                _modifyTeamState.value = _modifyTeamState.value.copy(
                    errorMessage = e.message
                )
            }
        }
    }


    internal fun updateTeam(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            try {
                val result = updateTeamUseCase.invoke(
                    accessToken = accessToken,
                    teamId = sharedViewModel.teamId.value ?: "",
                    name = _modifyTeamState.value.teamName,
                    introduction = _modifyTeamState.value.introduction,
                    rule = _modifyTeamState.value.rule,
                    matchType = MatchType.toDomain(_modifyTeamState.value.matchType),
                    access = Access.toDomain(_modifyTeamState.value.access!!),
                    dues = _modifyTeamState.value.dues.toIntOrNull() ?: 0
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
                        _modifyTeamState.value = _modifyTeamState.value.copy(
                            errorMessage = error.message
                        )
                    }
                )
            } catch (e: Exception) {
                Log.e("CreateTeamViewModel", "Exception during team creation", e)
                _uiState.value = UiState.Error(UiError.UnknownError("알 수 없는 오류가 발생했습니다."))
                _modifyTeamState.value = _modifyTeamState.value.copy(
                    errorMessage = e.message
                )
            }
        }
    }
}