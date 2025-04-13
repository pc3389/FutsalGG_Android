package com.futsalgg.app.presentation.user.profile

import androidx.lifecycle.ViewModel
import com.futsalgg.app.presentation.common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    // TODO State update after API
    private val _profileState = MutableStateFlow("")
    val profileState: StateFlow<String> = _profileState.asStateFlow()
}