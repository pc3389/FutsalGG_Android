package com.futsalgg.app.presentation.team.createteam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.team.model.Access
import com.futsalgg.app.domain.team.model.MatchType
import com.futsalgg.app.domain.team.usecase.CreateTeamUseCase
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.common.error.toUiError
import com.futsalgg.app.presentation.common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTeamViewModel @Inject constructor(
    private val createTeamUseCase: CreateTeamUseCase,
    private val tokenManager: ITokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _teamName = MutableStateFlow("")
    val teamName: StateFlow<String> = _teamName.asStateFlow()

    private val _introduction = MutableStateFlow("")
    val introduction: StateFlow<String> = _introduction.asStateFlow()

    private val _rule = MutableStateFlow("")
    val rule: StateFlow<String> = _rule.asStateFlow()

    private val _matchType = MutableStateFlow(MatchType.INTRA_SQUAD)
    val matchType: StateFlow<MatchType> = _matchType.asStateFlow()

    private val _access = MutableStateFlow(Access.TEAM_LEADER)
    val access: StateFlow<Access> = _access.asStateFlow()

    private val _dues = MutableStateFlow("")
    val dues: StateFlow<String> = _dues.asStateFlow()

    fun onTeamNameChange(name: String) {
        _teamName.value = name
    }

    fun onIntroductionChange(intro: String) {
        _introduction.value = intro
    }

    fun onRuleChange(rule: String) {
        _rule.value = rule
    }

    fun onMatchTypeChange(type: MatchType) {
        _matchType.value = type
    }

    fun onAccessChange(access: Access) {
        _access.value = access
    }

    fun onDuesChange(dues: String) {
        _dues.value = dues
    }

    fun createTeam(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val accessToken = tokenManager.getAccessToken()
                if (accessToken.isNullOrEmpty()) {
                    _uiState.value = UiState.Error(UiError.AuthError("AccessToken이 존재하지 않습니다"))
                    return@launch
                }

                val result = createTeamUseCase.createTeam(
                    accessToken = accessToken,
                    name = _teamName.value,
                    introduction = _introduction.value,
                    rule = _rule.value,
                    matchType = _matchType.value,
                    access = _access.value,
                    dues = _dues.value.toIntOrNull() ?: 0
                )

                result.fold(
                    onSuccess = {
                        _uiState.value = UiState.Success
                        onSuccess()
                    },
                    onFailure = { error ->
                        _uiState.value = UiState.Error(
                            (error as? DomainError)?.toUiError()
                                ?: UiError.UnknownError("팀 생성에 실패했습니다")
                        )
                    }
                )
            } catch (e: Exception) {
                _uiState.value = UiState.Error(UiError.UnknownError("알 수 없는 오류가 발생했습니다"))
            }
        }
    }
} 