package com.futsalgg.app.remote.api.match.model.request

import com.futsalgg.app.remote.api.match.model.response.MatchType

data class CreateMatchRequest(
    val teamId: String,
    val matchDate: String, // yyyy-MM-dd
    val type: MatchType,
    val location: String, // 최소 2글자
    val startTime: String? = null, // HH:mm
    val endTime: String? = null, // HH:mm
    val opponentTeamName: String? = null,
    val substituteTeamMemberId: String? = null, // 대리로 기록해주는 사람
    val description: String? = null, // 투표를 등록할 떄의 텍스트
    val isVote: Boolean // True: 투표를 생성할 경우, False: 경기일정을 생성할 경우
) 