package com.futsalgg.app.presentation.team.updateteam

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.team.usecase.GetTeamMembersByTeamIdUseCase
import com.futsalgg.app.presentation.common.SharedViewModel
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.common.error.toUiError
import com.futsalgg.app.presentation.common.model.TeamRole
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.presentation.team.teaminfo.TeamMemberState
import com.futsalgg.app.presentation.team.teaminfo.TeamMemberState.TeamMemberStatus.Companion.fromDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageTeamViewModel @Inject constructor(
    private val sharedViewModel: SharedViewModel,
    private val tokenManager: ITokenManager,
    private val getTeamMembersByTeamIdUseCase: GetTeamMembersByTeamIdUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    val teamState = sharedViewModel.teamState

    private val _teamMemberState = MutableStateFlow<List<TeamMemberState>>(emptyList())
    val teamMemberState: StateFlow<List<TeamMemberState>> =
        _teamMemberState.asStateFlow()

    init {
        loadTeamMembers()
        // TODO Remove stub when not needed
//        _teamMemberState.value = listOf(
//            TeamMemberState(
//                name = "닉네임",
//                status = TeamMemberState.TeamMemberStatus.ACTIVE
//            ),
//            TeamMemberState(
//                name = "바바닉네임",
//                status = TeamMemberState.TeamMemberStatus.ACTIVE
//            ),TeamMemberState(
//                name = "닉네임닉닉",
//                status = TeamMemberState.TeamMemberStatus.INACTIVE
//            ),TeamMemberState(
//                name = "아아닉네임",
//                status = TeamMemberState.TeamMemberStatus.INACTIVE
//            ),TeamMemberState(
//                name = "어어어닉네임",
//                status = TeamMemberState.TeamMemberStatus.PENDING
//            ),TeamMemberState(
//                name = "바바바닉네임",
//                status = TeamMemberState.TeamMemberStatus.PENDING
//            ),TeamMemberState(
//                name = "아아아아아아닉네임",
//                status = TeamMemberState.TeamMemberStatus.PENDING
//            ),TeamMemberState(
//                name = "닉네임",
//                status = TeamMemberState.TeamMemberStatus.ACTIVE
//            ),TeamMemberState(
//                name = "닉네임",
//                status = TeamMemberState.TeamMemberStatus.INACTIVE
//            ),TeamMemberState(
//                name = "닉네임",
//                status = TeamMemberState.TeamMemberStatus.ACTIVE
//            ),TeamMemberState(
//                name = "바바닉네임",
//                status = TeamMemberState.TeamMemberStatus.ACTIVE
//            ),TeamMemberState(
//                name = "닉네임닉닉",
//                status = TeamMemberState.TeamMemberStatus.INACTIVE
//            ),TeamMemberState(
//                name = "아아닉네임",
//                status = TeamMemberState.TeamMemberStatus.INACTIVE
//            ),TeamMemberState(
//                name = "어어어닉네임",
//                status = TeamMemberState.TeamMemberStatus.PENDING
//            ),TeamMemberState(
//                name = "바바바닉네임",
//                status = TeamMemberState.TeamMemberStatus.PENDING
//            ),TeamMemberState(
//                name = "아아아아아아닉네임",
//                status = TeamMemberState.TeamMemberStatus.PENDING
//            ),TeamMemberState(
//                name = "닉네임",
//                status = TeamMemberState.TeamMemberStatus.ACTIVE
//            ),TeamMemberState(
//                name = "닉네임",
//                status = TeamMemberState.TeamMemberStatus.INACTIVE
//            ),
//        )
    }

    private fun loadTeamMembers() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val accessToken = tokenManager.getAccessToken()

                if (accessToken.isNullOrEmpty()) {
                    Log.e("CreateMatchParticipantsViewModel", "엑세스 토큰이 존재하지 않습니다")
                    _uiState.value = UiState.Error(UiError.AuthError("엑세스 토큰이 존재하지 않습니다"))
                    return@launch
                }

                getTeamMembersByTeamIdUseCase(accessToken, sharedViewModel.teamId.value ?: "")
                    .onSuccess { teamMembers ->
                        _teamMemberState.value = teamMembers.map { member ->
                            TeamMemberState(
                                id = "",
                                name = member.name,
                                role = TeamRole.fromDomain(member.role),
                                profileUrl = member.profileUrl ?: "",
                                status = member.status.fromDomain(),
                                createdTime = member.createdTime
                            )
                        }
                        _uiState.value = UiState.Success
                    }
                    .onFailure { throwable ->
                        _uiState.value = UiState.Error(
                            (throwable as? DomainError)?.toUiError()
                                ?: UiError.UnknownError("알 수 없는 오류가 발생했습니다.")
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    UiError.UnknownError("예기치 않은 오류가 발생했습니다: ${e.message}")
                )
            }
        }
    }
}