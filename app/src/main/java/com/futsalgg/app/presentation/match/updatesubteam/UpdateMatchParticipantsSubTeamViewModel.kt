package com.futsalgg.app.presentation.match.updatesubteam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.match.model.SubTeam
import com.futsalgg.app.domain.match.usecase.UpdateMatchParticipantsSubTeamUseCase
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.common.error.toUiError
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.presentation.match.MatchSharedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateMatchParticipantsSubTeamViewModel @Inject constructor(
    private val updateMatchParticipantsSubTeamUseCase: UpdateMatchParticipantsSubTeamUseCase,
    private val matchSharedViewModel: MatchSharedViewModel,
    private val tokenManager: ITokenManager
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _selectedParticipantIds = MutableStateFlow<List<String>>(emptyList())

    private val _unSelectedParticipantIds = MutableStateFlow<List<String>>(emptyList())

    val matchParticipantsState = matchSharedViewModel.matchParticipantsState

    val matchState = matchSharedViewModel.matchState

    fun isSelected(participantId: String) {
        if (_selectedParticipantIds.value.contains(participantId)) {
            // 이미 선택된 경우 선택 해제
            _selectedParticipantIds.value -= participantId
            _unSelectedParticipantIds.value += participantId
        } else {
            // 선택되지 않은 경우 선택
            _selectedParticipantIds.value += participantId
            _unSelectedParticipantIds.value -= participantId
        }
    }

    fun updateMatchParticipantsSubTeam(
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val accessToken = tokenManager.getAccessToken() ?: ""
            val matchId = matchSharedViewModel.matchState.value.id
            try {
                _uiState.value = UiState.Loading

                updateTeamA(
                    accessToken,
                    matchId,
                    onSuccess
                )
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    UiError.UnknownError("[updateMatchParticipantsSubTeam] 예기치 않은 오류가 발생했습니다: ${e.message}")
                )
            }
        }
    }

    fun updateMatchParticipantsSubTeamInSharedVM(index: Int) {
        matchSharedViewModel.updateParticipantSubteam(index)
    }

    private suspend fun updateTeamA(
        accessToken: String,
        matchId: String,
        onSuccess: () -> Unit
    ) {
        updateMatchParticipantsSubTeamUseCase(
            accessToken = accessToken,
            matchId = matchId,
            participantIds = _selectedParticipantIds.value,
            subTeam = SubTeam.A
        ).onSuccess {
            _uiState.value = UiState.Success
            updateTeamB(
                accessToken = accessToken,
                matchId = matchId,
                onSuccess = onSuccess
            )
        }.onFailure { error ->
            _uiState.value = UiState.Error(
                (error as? DomainError)?.toUiError()
                    ?: UiError.UnknownError("[updateTeamA] 알 수 없는 오류가 발생했습니다: ${error.message}")
            )
        }
    }

    private suspend fun updateTeamB(
        accessToken: String,
        matchId: String,
        onSuccess: () -> Unit
    ) {
        updateMatchParticipantsSubTeamUseCase(
            accessToken = accessToken,
            matchId = matchId,
            participantIds = _unSelectedParticipantIds.value,
            subTeam = SubTeam.B
        ).onSuccess {
            _uiState.value = UiState.Success
            onSuccess()
        }.onFailure { error ->
            _uiState.value = UiState.Error(
                (error as? DomainError)?.toUiError()
                    ?: UiError.UnknownError("[updateTeamB] 알 수 없는 오류가 발생했습니다: ${error.message}")
            )
        }
    }
}