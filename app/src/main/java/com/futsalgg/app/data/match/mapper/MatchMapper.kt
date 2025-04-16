package com.futsalgg.app.data.match.mapper

import com.futsalgg.app.remote.api.match.model.response.MatchResponse
import com.futsalgg.app.domain.match.model.Match as DomainMatch
import com.futsalgg.app.domain.match.model.MatchStatus as DomainMatchStatus
import com.futsalgg.app.domain.common.model.MatchType as DomainMatchType
import com.futsalgg.app.domain.match.model.VoteStatus as DomainVoteStatus

fun MatchResponse.toDomain(): DomainMatch = DomainMatch(
    id = id,
    opponentTeamName = opponentTeamName,
    description = description,
    type = DomainMatchType.valueOf(type.name),
    matchDate = matchDate,
    startTime = startTime,
    endTime = endTime,
    location = location,
    voteStatus = DomainVoteStatus.valueOf(voteStatus.name),
    status = DomainMatchStatus.valueOf(status.name),
    createdTime = createdTime
)