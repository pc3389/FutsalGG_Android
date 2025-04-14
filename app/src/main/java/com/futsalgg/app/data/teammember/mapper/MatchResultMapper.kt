package com.futsalgg.app.data.teammember.mapper

import com.futsalgg.app.domain.match.model.MatchResult as DomainMatchResult
import com.futsalgg.app.remote.api.teammember.model.response.GetTeamMemberResponse.MatchResult as RemoteMatchResult

fun RemoteMatchResult.toDomain(): DomainMatchResult {
    return when (this) {
        RemoteMatchResult.WIN -> DomainMatchResult.WIN
        RemoteMatchResult.LOSE -> DomainMatchResult.LOSE
        RemoteMatchResult.DRAW -> DomainMatchResult.DRAW
    }
} 