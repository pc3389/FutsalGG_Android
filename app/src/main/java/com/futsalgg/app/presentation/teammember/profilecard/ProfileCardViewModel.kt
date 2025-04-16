package com.futsalgg.app.presentation.teammember.profilecard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.team.usecase.GetTeamMemberForProfileUseCase
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.common.error.toUiError
import com.futsalgg.app.presentation.common.mapper.RoleMapper
import com.futsalgg.app.presentation.common.model.MatchResult
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.presentation.main.model.TeamRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class ProfileCardViewModel @Inject constructor(
    private val getTeamMemberForProfileUseCase: GetTeamMemberForProfileUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _profileState = MutableStateFlow(ProfileCardState.Initial)
    val profileState: StateFlow<ProfileCardState> = _profileState.asStateFlow()

    init {
        stubProfile()
    }

    private fun stubProfile() {
        _profileState.value = ProfileCardState(
            name = "닉네임",
            birthday = "1992.02.21",
            squadNumber = 32,
            profileUrl = "",
            role = TeamRole.TEAM_LEADER,
            createdTime = "2025.04.16",
            teamName = "팀이름",
            teamLogoUrl = null,
            totalGameNum = 20,
            history = listOf(
                MatchHistory("11", MatchResult.WIN),
                MatchHistory("11", MatchResult.DRAW),
                MatchHistory("11", MatchResult.LOSE),
                MatchHistory("11", MatchResult.DRAW),
                MatchHistory("11", MatchResult.WIN),
                MatchHistory("11", MatchResult.WIN),
                MatchHistory("11", MatchResult.LOSE)
            )
        )
    }
    fun getProfile(accessToken: String, id: String? = null) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            getTeamMemberForProfileUseCase(accessToken, id)
                .onSuccess { teamMember ->
                    // TODO SquadNumber, profileUrl and TeamLogoUrl update
                    _profileState.value = ProfileCardState(
                        name = teamMember.name,
                        birthday = teamMember.birthDate,
                        squadNumber = null,
                        profileUrl = "",
                        role = RoleMapper.roleMapper(teamMember.team.role),
                        createdTime = teamMember.createdTime,
                        teamName = teamMember.team.name,
                        teamLogoUrl = null,
                        totalGameNum = teamMember.match.total,
                        history = teamMember.match.history.map {
                            when (it.result.name) {
                                MatchResult.WIN.name -> MatchHistory(it.id, MatchResult.WIN)
                                MatchResult.DRAW.name -> MatchHistory(it.id, MatchResult.DRAW)
                                MatchResult.LOSE.name -> MatchHistory(it.id, MatchResult.LOSE)
                                else -> MatchHistory(it.id, MatchResult.DRAW)
                            }

                        }
                    )
                    _uiState.value = UiState.Success
                }
                .onFailure { throwable ->
                    _uiState.value = UiState.Error(
                        (throwable as? DomainError)?.toUiError()
                            ?: UiError.UnknownError("알 수 없는 오류가 발생했습니다.")
                    )
                }
        }
    }


    fun getAgeGroup(
        birthday: String
    ): String {
        return try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val birthDate = LocalDate.parse(birthday, formatter)
            val currentDate = LocalDate.now()
            val age = ChronoUnit.YEARS.between(birthDate, currentDate)

            when {
                age < 20 -> "10"
                age < 30 -> "20"
                age < 40 -> "30"
                age < 50 -> "40"
                age < 60 -> "50"
                age < 70 -> "60"
                age < 80 -> "70"
                age < 90 -> "80"
                else -> "90"
            }
        } catch (e: Exception) {
            "00"
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

        history.forEach{
            if (it.result == MatchResult.WIN) win++
            else if (it.result == MatchResult.DRAW) draw++
            else lose++
        }

        return "$win/$draw/$lose"
    }
}