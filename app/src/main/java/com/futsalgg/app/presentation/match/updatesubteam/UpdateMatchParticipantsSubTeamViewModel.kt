package com.futsalgg.app.presentation.match.updatesubteam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.match.model.SubTeam
import com.futsalgg.app.domain.match.usecase.UpdateMatchParticipantsSubTeamUseCase
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
class UpdateMatchParticipantsSubTeamViewModel @Inject constructor(
    private val selectedMatchId: String,
    private val updateMatchParticipantsSubTeamUseCase: UpdateMatchParticipantsSubTeamUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _selectedParticipantIds = MutableStateFlow<List<String>>(emptyList())
    val selectedParticipantIds: StateFlow<List<String>> = _selectedParticipantIds.asStateFlow()

    private val _unSelectedParticipantIds = MutableStateFlow<List<String>>(emptyList())
    val unSelectedParticipantIds: StateFlow<List<String>> = _unSelectedParticipantIds.asStateFlow()

    fun isSelected(participantId: String) {
        if (_selectedParticipantIds.value.contains(participantId)) {
            // 이미 선택된 경우 선택 해제
            _selectedParticipantIds.value = _selectedParticipantIds.value - participantId
            _unSelectedParticipantIds.value = _unSelectedParticipantIds.value + participantId
        } else {
            // 선택되지 않은 경우 선택
            _selectedParticipantIds.value = _selectedParticipantIds.value + participantId
            _unSelectedParticipantIds.value = _unSelectedParticipantIds.value - participantId
        }
    }

    fun updateMatchParticipantsSubTeam(
        accessToken: String
    ) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            updateTeamA(accessToken)
            updateTeamB(accessToken)
        }
    }

    private suspend fun updateTeamA(accessToken: String) {
        updateMatchParticipantsSubTeamUseCase(
            accessToken = accessToken,
            matchId = selectedMatchId,
            participantIds = _selectedParticipantIds.value,
            subTeam = SubTeam.A
        ).onSuccess {
            _uiState.value = UiState.Success
        }.onFailure { exception ->
            _uiState.value = UiState.Error(
                (exception as? DomainError)?.toUiError()
                    ?: UiError.UnknownError("알 수 없는 오류가 발생했습니다.")
            )
        }
    }

    private suspend fun updateTeamB(accessToken: String) {
        updateMatchParticipantsSubTeamUseCase(
            accessToken = accessToken,
            matchId = selectedMatchId,
            participantIds = _unSelectedParticipantIds.value,
            subTeam = SubTeam.B
        ).onSuccess {
            _uiState.value = UiState.Success
        }.onFailure { exception ->
            _uiState.value = UiState.Error(
                (exception as? DomainError)?.toUiError()
                    ?: UiError.UnknownError("알 수 없는 오류가 발생했습니다.")
            )
        }
    }
}