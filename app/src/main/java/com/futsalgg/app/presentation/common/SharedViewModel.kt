package com.futsalgg.app.presentation.common

import androidx.lifecycle.ViewModel
import com.futsalgg.app.presentation.main.model.MyTeam
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
    private val _myTeamMemberId = MutableStateFlow<String?>(null)
    val myTeamMemberId: StateFlow<String?> = _myTeamMemberId.asStateFlow()

    // My Team State
    private val _teamState: MutableStateFlow<MyTeam?> = MutableStateFlow(null)
    val teamState: StateFlow<MyTeam?> = _teamState.asStateFlow()

    // Selected Team Member Id
    private val _selectedTeamMemberId = MutableStateFlow<String?>(null)
    val selectedTeamMemberId: StateFlow<String?> = _selectedTeamMemberId.asStateFlow()


    // teamId 설정
    fun setTeamId(id: String) {
        _teamId.value = id
    }

    // teamMemberId 설정
    fun setMyTeamMemberId(id: String) {
        _myTeamMemberId.value = id
    }

    // 모든 상태 초기화
    fun clearAll() {
        _teamId.value = null
        _myTeamMemberId.value = null
    }

    // Team State 설정
    fun updateTeamState(team: MyTeam) {
        _teamState.value = team
    }

    fun clearSelectedTeamMemberId() {
        _selectedTeamMemberId.value = null
    }

    fun setSelectedTeamMemberId(id: String) {
        _selectedTeamMemberId.value = id
    }
}