package com.futsalgg.app.presentation.common.mapper

import com.futsalgg.app.domain.common.model.MatchType
import com.futsalgg.app.domain.match.model.Match
import com.futsalgg.app.domain.match.model.MatchStatus
import com.futsalgg.app.domain.match.model.VoteStatus
import com.futsalgg.app.remote.api.match.model.response.GetMatchResponse
import com.futsalgg.app.remote.api.match.model.response.MatchResponse

fun MatchResponse.toDomain(): Match = Match(
    id = id,
    opponentTeamName = opponentTeamName,
    description = description,
    type = MatchType.valueOf(type.name),
    matchDate = matchDate,
    startTime = startTime,
    endTime = endTime,
    location = location,
    voteStatus = VoteStatus.valueOf(voteStatus.name),
    status = MatchStatus.valueOf(status.name),
    createdTime = createdTime
)

fun GetMatchResponse.toDomain(): Match = Match(
    id = id,
    opponentTeamName = opponentTeamName,
    description = null,
    type = MatchType.valueOf(type),
    matchDate = matchDate,
    startTime = startTime,
    endTime = endTime,
    location = location,
    voteStatus = VoteStatus.NONE,
    status = MatchStatus.ONGOING,
    createdTime = createdTime
)