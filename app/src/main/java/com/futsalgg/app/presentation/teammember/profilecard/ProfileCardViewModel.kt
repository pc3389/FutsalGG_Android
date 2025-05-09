package com.futsalgg.app.presentation.teammember.profilecard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.team.usecase.GetTeamMemberForProfileUseCase
import com.futsalgg.app.presentation.common.SharedViewModel
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.common.error.toUiError
import com.futsalgg.app.presentation.common.model.MatchResult
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.presentation.common.model.TeamRole
import com.futsalgg.app.util.dateToRequestFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileCardViewModel @Inject constructor(
    private val getTeamMemberForProfileUseCase: GetTeamMemberForProfileUseCase,
    private val tokenManager: ITokenManager,
    sharedViewModel: SharedViewModel
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _profileState = MutableStateFlow(ProfileCardState.Initial)
    val profileState: StateFlow<ProfileCardState> = _profileState.asStateFlow()

    private val selectedTeamMemberId = sharedViewModel.selectedTeamMemberId.value

    init {
        getProfile(selectedTeamMemberId)
    }

    private fun getProfile(id: String? = null) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val accessToken = tokenManager.getAccessToken() ?: ""

                getTeamMemberForProfileUseCase(accessToken, id)
                    .onSuccess { teamMember ->
                        _profileState.value = ProfileCardState(
                            id = teamMember.id,
                            name = teamMember.name,
                            birthday = teamMember.birthDate.dateToRequestFormat(),
                            squadNumber = teamMember.squadNumber,
                            profileUrl = teamMember.profileUrl,
                            generation = teamMember.generation,
                            role = TeamRole.fromDomain(teamMember.team.role),
                            createdTime = teamMember.createdTime,
                            teamName = teamMember.team.name,
                            teamLogoUrl = null,
                            totalGameNum = teamMember.match.total,
                            history = teamMember.match.history?.map {
                                when (it.result.name) {
                                    MatchResult.WIN.name -> MatchHistory(it.id, MatchResult.WIN)
                                    MatchResult.DRAW.name -> MatchHistory(it.id, MatchResult.DRAW)
                                    MatchResult.LOSE.name -> MatchHistory(it.id, MatchResult.LOSE)
                                    else -> MatchHistory(it.id, MatchResult.DRAW)
                                }
                            } ?: listOf()
                        )
                        _uiState.value = UiState.Success
                    }
                    .onFailure { error ->
                        _uiState.value = UiState.Error(
                            (error as? DomainError)?.toUiError()
                                ?: UiError.UnknownError("[getProfile] 알 수 없는 오류가 발생했습니다: ${error.message}")
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    UiError.UnknownError("[getProfile] 예기치 않은 오류가 발생했습니다: ${e.message}")
                )
            }
        }
    }

    fun getWinRate(history: List<MatchHistory>): Int {
        val win = history.filter { it.result == MatchResult.WIN }

        return if (history.isEmpty()) 0 else 100 * win.size / history.size
    }

    fun getStatString(history: List<MatchHistory>): String {
        var win = 0
        var draw = 0
        var lose = 0

        history.forEach {
            when (it.result) {
                MatchResult.WIN -> win++
                MatchResult.DRAW -> draw++
                else -> lose++
            }
        }

        return "$win/$draw/$lose"
    }
}