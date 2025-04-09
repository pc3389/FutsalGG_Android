package com.futsalgg.app.data.match.mapper

import com.futsalgg.app.data.match.model.Match as DataMatch
import com.futsalgg.app.data.match.model.MatchStatus as DataMatchStatus
import com.futsalgg.app.data.match.model.MatchType as DataMatchType
import com.futsalgg.app.data.match.model.VoteStatus as DataVoteStatus
import com.futsalgg.app.domain.match.model.Match as DomainMatch
import com.futsalgg.app.domain.match.model.MatchStatus as DomainMatchStatus
import com.futsalgg.app.domain.common.model.MatchType as DomainMatchType
import com.futsalgg.app.domain.match.model.VoteStatus as DomainVoteStatus
import com.futsalgg.app.remote.api.match.model.response.MatchResponse

fun MatchResponse.toData(): DataMatch = DataMatch(
    id = id,
    opponentTeamName = opponentTeamName,
    description = description,
    type = DataMatchType.valueOf(type.name),
    matchDate = matchDate,
    startTime = startTime,
    endTime = endTime,
    location = location,
    voteStatus = DataVoteStatus.valueOf(voteStatus.name),
    status = DataMatchStatus.valueOf(status.name),
    createdTime = createdTime
)

fun DataMatch.toDomain(): DomainMatch = DomainMatch(
    id = id,
    opponentTeamName = opponentTeamName,
    description = description,
    type = type.toDomain(),
    matchDate = matchDate,
    startTime = startTime,
    endTime = endTime,
    location = location,
    voteStatus = voteStatus.toDomain(),
    status = status.toDomain(),
    createdTime = createdTime
)

fun DataMatchType.toDomain(): DomainMatchType = DomainMatchType.valueOf(this.name)
fun DataVoteStatus.toDomain(): DomainVoteStatus = DomainVoteStatus.valueOf(this.name)
fun DataMatchStatus.toDomain(): DomainMatchStatus = DomainMatchStatus.valueOf(this.name) 