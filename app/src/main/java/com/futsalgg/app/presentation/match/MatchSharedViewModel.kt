package com.futsalgg.app.presentation.match

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@HiltViewModel
class MatchSharedViewModel @Inject constructor() : ViewModel() {
    private val _selectedMatchId = MutableStateFlow("")
    val selectedMatchId: StateFlow<String> = _selectedMatchId
}