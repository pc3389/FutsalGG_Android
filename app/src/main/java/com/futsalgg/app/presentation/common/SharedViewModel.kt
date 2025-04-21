package com.futsalgg.app.presentation.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedViewModel @Inject constructor() : ViewModel() {
    // teamId 상태 관리
    private val _teamId = MutableStateFlow<String?>(null)
    val teamId: StateFlow<String?> = _teamId.asStateFlow()

    // teamId 상태 관리
    private val _teamMemberId = MutableStateFlow<String?>(null)
    val teamMemberId: StateFlow<String?> = _teamMemberId.asStateFlow()

    // teamId 설정
    fun setTeamId(id: String) {
        _teamId.value = id
    }

    // teamMemberId 설정
    fun setTeamMemberId(id: String) {
        _teamMemberId.value = id
    }

    // 모든 상태 초기화
    fun clearAll() {
        _teamId.value = null
        _teamMemberId.value = null
    }
}