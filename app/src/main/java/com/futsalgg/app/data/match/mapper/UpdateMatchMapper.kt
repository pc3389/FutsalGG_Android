package com.futsalgg.app.data.match.mapper

import com.futsalgg.app.domain.match.model.UpdateMatch
import com.futsalgg.app.remote.api.match.model.response.UpdateMatchResponse

fun UpdateMatchResponse.toDomain(): UpdateMatch = UpdateMatch(
    id = id,
    substituteTeamMemberId = substituteTeamMemberId,
    opponentTeamName = opponentTeamName,
    description = description,
    rounds = rounds,
    type = type,
    matchDate = matchDate,
    startTime = startTime,
    endTime = endTime,
    location = location,
    voteStatus = voteStatus,
    status = status,
    createdTime = createdTime
) 