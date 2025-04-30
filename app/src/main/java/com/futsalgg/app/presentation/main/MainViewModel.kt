package com.futsalgg.app.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.team.usecase.GetMyTeamUseCase
import com.futsalgg.app.domain.match.usecase.GetRecentMatchDateUseCase
import com.futsalgg.app.presentation.common.SharedViewModel
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.common.error.toUiError
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.presentation.main.model.MyTeam
import com.futsalgg.app.presentation.main.model.MyTeamMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MainState(
    val myTeam: MyTeam = MyTeam(),
    val recentMatchDate: String = ""
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMyTeamUseCase: GetMyTeamUseCase,
    private val getRecentMatchDateUseCase: GetRecentMatchDateUseCase,
    private val tokenManager: ITokenManager,
    private val sharedViewModel: SharedViewModel
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _mainState = MutableStateFlow(MainState())
    val mainState: StateFlow<MainState> = _mainState.asStateFlow()

    init {
        getMyTeam()
    }

    private fun getMyTeam() {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading

                val accessToken = tokenManager.getAccessToken()

                if (accessToken.isNullOrEmpty()) {
                    Log.e("CreateTeamViewModel", "엑세스 토큰이 존재하지 않습니다")
                    _uiState.value = UiState.Error(UiError.AuthError("엑세스 토큰이 존재하지 않습니다"))
                    return@launch
                }

                getMyTeamUseCase(accessToken)
                    .onSuccess { domainMyTeam ->
                        _uiState.value = UiState.Success
                        val myTeam = MyTeamMapper.toPresentation(
                            domainMyTeam
                        )
                        _mainState.update {
                            it.copy(
                                myTeam = myTeam
                            )
                        }
                        sharedViewModel.updateTeamState(myTeam)
                        sharedViewModel.setTeamId(domainMyTeam.id)
                        sharedViewModel.setMyTeamMemberId(domainMyTeam.teamMemberId)
                        
                        // 팀 정보를 가져온 후 최근 경기 날짜 조회
                        getRecentMatchDate(accessToken, domainMyTeam.id)
                    }
                    .onFailure { error ->
                        _uiState.value = UiState.Error(
                            (error as? DomainError)?.toUiError()
                                ?: UiError.UnknownError("프레젠 알 수 없는 오류가 발생했습니다.")
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(UiError.UnknownError("프레젠 알 수 없는 오류가 발생했습니다."))
            }
        }
    }

    private fun getRecentMatchDate(accessToken: String, teamId: String) {
        viewModelScope.launch {
            try {
                getRecentMatchDateUseCase(accessToken, teamId)
                    .onSuccess { date ->
                        _mainState.update {
                            it.copy(recentMatchDate = date)
                        }
                    }
                    .onFailure { error ->
                        Log.e("MainViewModel", "최근 경기 날짜 조회 실패: ${error.message}")
                    }
            } catch (e: Exception) {
                Log.e("MainViewModel", "최근 경기 날짜 조회 중 오류 발생: ${e.message}")
            }
        }
    }
}