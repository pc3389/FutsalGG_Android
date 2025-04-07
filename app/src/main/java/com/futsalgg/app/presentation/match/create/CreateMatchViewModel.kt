package com.futsalgg.app.presentation.match.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.match.usecase.CreateMatchUseCase
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.match.model.MatchType
import com.futsalgg.app.presentation.common.state.DateState
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.util.isValidDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateMatchViewModel @Inject constructor(
    private val createMatchUseCase: CreateMatchUseCase,
    private val tokenManager: ITokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _createMatchState = MutableStateFlow(CreateMatchState())
    val createMatchState: StateFlow<CreateMatchState> = _createMatchState.asStateFlow()

    internal fun onTypeChange(type: MatchType) {
        _createMatchState.value = _createMatchState.value.copy(
            type = type,
        )
    }
//
//    internal fun onOpponentTeamNameChange(newValue: String) {
//        _createMatchState.value = _createMatchState.value.copy(
//            opponentTeamName = newValue,
//            opponentTeamNameState = EditTextState.Initial
//        )
//    }
//
//    internal fun onLocationChange(newValue: String) {
//        _createMatchState.value = _createMatchState.value.copy(
//            location = newValue
//        )
//    }
//
//    internal fun onMatchDateChange(newValue: String) {
//        _createMatchState.value = _createMatchState.value.copy(
//            matchDate = newValue,
//            matchDateState = EditTextState.Initial
//        )
//    }
//
//    internal fun onStartTimeChange(newValue: String) {
//        _createMatchState.value = _createMatchState.value.copy(
//            startTime = newValue,
//            startTimeState = EditTextState.Initial
//        )
//    }
//
//    internal fun onEndTimeChange(newValue: String) {
//        _createMatchState.value = _createMatchState.value.copy(
//            endTime = newValue,
//            endTimeState = EditTextState.Initial
//        )
//    }
//
//    internal fun onDescriptionChange(newValue: String) {
//        _createMatchState.value = _createMatchState.value.copy(
//            description = newValue
//        )
//    }
//
//    internal fun onTeamIdChange(newValue: String) {
//        _createMatchState.value = _createMatchState.value.copy(
//            teamId = newValue
//        )
//    }
//
//    internal fun onIsVoteChange(newValue: Boolean) {
//        _createMatchState.value = _createMatchState.value.copy(
//            isVote = newValue
//        )
//    }

    internal fun createMatch(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val accessToken = tokenManager.getAccessToken()

                if (accessToken.isNullOrEmpty()) {
                    _uiState.value = UiState.Error(UiError.AuthError("엑세스 토큰이 존재하지 않습니다"))
                    return@launch
                }

                val result = createMatchUseCase(
                    accessToken = accessToken,
                    teamId = _createMatchState.value.teamId,
                    matchDate = _createMatchState.value.matchDate,
                    type = MatchType.toDomain(_createMatchState.value.type),
                    location = _createMatchState.value.location,
                    startTime = _createMatchState.value.startTime.takeIf { it.isNotEmpty() },
                    endTime = _createMatchState.value.endTime.takeIf { it.isNotEmpty() },
                    opponentTeamName = _createMatchState.value.opponentTeamName.takeIf { it.isNotEmpty() },
                    description = _createMatchState.value.description.takeIf { it.isNotEmpty() },
                    isVote = _createMatchState.value.isVote
                )

                if (result.isSuccess) {
                    _uiState.value = UiState.Success
                    onSuccess()
                } else {
                    _uiState.value = UiState.Error(UiError.UnknownError(result.exceptionOrNull()?.message ?: "알 수 없는 오류가 발생했습니다"))
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(UiError.UnknownError(e.message ?: "알 수 없는 오류가 발생했습니다"))
            }
        }
    }

    internal fun onValidateMatchDate(value: String) {
        val state = _createMatchState.value
        _createMatchState.value = state.copy(
            matchDate = value,
            matchDateState = if (value.isEmpty()) {
                DateState.Initial
            } else isValidDate(value)
        )
    }
} 