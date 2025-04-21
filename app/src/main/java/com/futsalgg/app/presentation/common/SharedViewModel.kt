package com.futsalgg.app.presentation.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedViewModel @Inject constructor() : ViewModel() {
    // userId 상태 관리
    private val _userId = MutableStateFlow<String?>(null)
    val userId: StateFlow<String?> = _userId.asStateFlow()

    // teamId 상태 관리
    private val _teamId = MutableStateFlow<String?>(null)
    val teamId: StateFlow<String?> = _teamId.asStateFlow()

    // teamId 상태 관리
    private val _teamMemberId = MutableStateFlow<String?>(null)
    val teamMemberId: StateFlow<String?> = _teamMemberId.asStateFlow()

    // userId 설정
    fun setUserId(id: String) {
        _userId.value = id
    }

    // teamId 설정
    fun setTeamId(id: String) {
        _teamId.value = id
    }

    // teamMemberId 설정
    fun setTeamMemberId(id: String) {
        _teamId.value = id
    }

    // 모든 상태 초기화
    fun clearAll() {
        _userId.value = null
        _teamId.value = null
        _teamMemberId.value = null
    }
}