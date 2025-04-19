package com.futsalgg.app.remote.api.match

import com.futsalgg.app.remote.api.match.model.request.CreateMatchRequest
import com.futsalgg.app.remote.api.match.model.request.CreateMatchParticipantsRequest
import com.futsalgg.app.remote.api.match.model.request.UpdateMatchParticipantsSubTeamRequest
import com.futsalgg.app.remote.api.match.model.request.UpdateMatchRoundsRequest
import com.futsalgg.app.remote.api.match.model.request.CreateMatchStatRequest
import com.futsalgg.app.remote.api.match.model.response.GetMatchesResponse
import com.futsalgg.app.remote.api.match.model.response.MatchResponse
import com.futsalgg.app.remote.api.match.model.response.CreateMatchParticipantsResponse
import com.futsalgg.app.remote.api.match.model.response.GetMatchResponse
import com.futsalgg.app.remote.api.match.model.response.MatchParticipant
import com.futsalgg.app.remote.api.match.model.response.GetMatchStatsResponse
import com.futsalgg.app.remote.api.match.model.response.MatchStat
import com.futsalgg.app.remote.api.match.model.response.MatchStatus
import com.futsalgg.app.remote.api.match.model.response.VoteStatus
import retrofit2.Response
import java.util.UUID
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class StubMatchApi : MatchApi {
    override suspend fun getMatches(
        authHeader: String,
        page: Int,
        size: Int
    ): Response<GetMatchesResponse> {
        return Response.success(GetMatchesResponse(emptyList()))
    }

    override suspend fun getMatch(
        authHeader: String,
        id: String
    ): Response<GetMatchResponse> {
        return Response.success(
            GetMatchResponse(
                id = id,
                type = "FRIENDLY",
                matchDate = "2024-03-20",
                startTime = "19:00",
                endTime = "20:00",
                location = "서울 풋살장",
                opponentTeamName = "상대팀",
                mom = GetMatchResponse.Mom(
                    profileUrl = "https://example.com/profile.jpg",
                    name = "홍길동"
                ),
                createdTime = "2024-03-20T19:00:00"
            )
        )
    }

    override suspend fun deleteMatch(
        authHeader: String,
        id: String
    ): Response<Unit> {
        return Response.success(Unit)
    }

    override suspend fun createMatch(
        authHeader: String,
        request: CreateMatchRequest
    ): Response<MatchResponse> {
        return Response.success(
            MatchResponse(
                id = UUID.randomUUID().toString(),
                type = request.type,
                matchDate = request.matchDate,
                startTime = request.startTime,
                endTime = request.endTime,
                location = request.location,
                opponentTeamName = request.opponentTeamName,
                description = "TODO()",
                voteStatus = VoteStatus.NONE,
                status = MatchStatus.COMPLETED,
                createdTime = "2024-03-20T19:00:00",
            )
        )
    }

    override suspend fun createMatchParticipants(
        accessToken: String,
        request: CreateMatchParticipantsRequest
    ): Response<CreateMatchParticipantsResponse> {
        val participants = request.teamMemberIds.map { id ->
            MatchParticipant(
                id = UUID.randomUUID().toString(),
                matchId = request.matchId,
                teamMemberId = id,
                name = "테스트 플레이어",
                role = "MEMBER",
                subTeam = "NONE",
                createdTime = "2024-03-20T19:00:00",
                profileUrl = null
            )
        }
        return Response.success(CreateMatchParticipantsResponse(participants))
    }

    override suspend fun updateMatchParticipantsSubTeam(
        accessToken: String,
        request: UpdateMatchParticipantsSubTeamRequest
    ): Response<Unit> {
        return Response.success(Unit)
    }

    override suspend fun updateMatchRounds(
        accessToken: String,
        matchId: String,
        request: UpdateMatchRoundsRequest
    ): Response<Unit> {
        return Response.success(Unit)
    }

    override suspend fun getMatchParticipants(
        accessToken: String,
        matchId: String
    ): Response<List<MatchParticipant>> {
        return Response.success(
            listOf(
                MatchParticipant(
                    id = "1123123",
                    matchId = matchId,
                    teamMemberId = "1324667",
                    name = "테스트 선수 A 1",
                    role = "PLAYER",
                    subTeam = "A",
                    createdTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
                        .toString(),
                    profileUrl = ""
                ),MatchParticipant(
                    id = "1235",
                    matchId = matchId,
                    teamMemberId = "112341",
                    name = "테스트 선수 A 2",
                    role = "PLAYER",
                    subTeam = "A",
                    createdTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
                        .toString(),
                    profileUrl = ""
                ),MatchParticipant(
                    id = "1134626",
                    matchId = matchId,
                    teamMemberId = "1234632465",
                    name = "테스트 선수 A 3",
                    role = "PLAYER",
                    subTeam = "A",
                    createdTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
                        .toString(),
                    profileUrl = ""
                ),MatchParticipant(
                    id = "17171324",
                    matchId = matchId,
                    teamMemberId = "112351235",
                    name = "테스트 선수 A 4",
                    role = "PLAYER",
                    subTeam = "A",
                    createdTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
                        .toString(),
                    profileUrl = ""
                ),MatchParticipant(
                    id = "12532151",
                    matchId = matchId,
                    teamMemberId = "11245324626",
                    name = "테스트 선수 A 1",
                    role = "PLAYER",
                    subTeam = "A",
                    createdTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
                        .toString(),
                    profileUrl = ""
                ),MatchParticipant(
                    id = "1324627",
                    matchId = matchId,
                    teamMemberId = "123521341",
                    name = "테스트 선수 A 2",
                    role = "PLAYER",
                    subTeam = "A",
                    createdTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
                        .toString(),
                    profileUrl = ""
                ),MatchParticipant(
                    id = "274561",
                    matchId = matchId,
                    teamMemberId = "11823",
                    name = "테스트 선수 A 3",
                    role = "PLAYER",
                    subTeam = "A",
                    createdTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
                        .toString(),
                    profileUrl = ""
                ),MatchParticipant(
                    id = "24561",
                    matchId = matchId,
                    teamMemberId = "1123546727",
                    name = "테스트 선수 A 4",
                    role = "PLAYER",
                    subTeam = "A",
                    createdTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
                        .toString(),
                    profileUrl = ""
                ),MatchParticipant(
                    id = "234623461",
                    matchId = matchId,
                    teamMemberId = "124362345",
                    name = "테스트 선수 A 1",
                    role = "PLAYER",
                    subTeam = "A",
                    createdTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
                        .toString(),
                    profileUrl = ""
                ),
                MatchParticipant(
                    id = "268769",
                    matchId = matchId,
                    teamMemberId = "24563426",
                    name = "테스트 선수 B 1",
                    role = "PLAYER",
                    subTeam = "B",
                    createdTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
                        .toString(),
                    profileUrl = ""
                ),MatchParticipant(
                    id = "1678678",
                    matchId = matchId,
                    teamMemberId = "35261",
                    name = "테스트 선수 A 2",
                    role = "PLAYER",
                    subTeam = "A",
                    createdTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
                        .toString(),
                    profileUrl = ""
                ),
                MatchParticipant(
                    id = "2678678",
                    matchId = matchId,
                    teamMemberId = "2125234",
                    name = "테스트 선수 B 2",
                    role = "PLAYER",
                    subTeam = "B",
                    createdTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
                        .toString(),
                    profileUrl = ""
                ),MatchParticipant(
                    id = "1",
                    matchId = matchId,
                    teamMemberId = "613512341",
                    name = "테스트 선수 A 3",
                    role = "PLAYER",
                    subTeam = "A",
                    createdTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
                        .toString(),
                    profileUrl = ""
                ),
                MatchParticipant(
                    id = "2",
                    matchId = matchId,
                    teamMemberId = "234523452",
                    name = "테스트 선수 B 3",
                    role = "PLAYER",
                    subTeam = "B",
                    createdTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
                        .toString(),
                    profileUrl = ""
                ),MatchParticipant(
                    id = "1",
                    matchId = matchId,
                    teamMemberId = "112341234",
                    name = "테스트 선수 A 4",
                    role = "PLAYER",
                    subTeam = "A",
                    createdTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
                        .toString(),
                    profileUrl = ""
                )
            )
        )
    }

    override suspend fun getMatchStats(
        accessToken: String,
        matchId: String
    ): Response<GetMatchStatsResponse> {
        val stats = mapOf(
            "1" to mapOf(
                "A" to listOf(
                    listOf(
                        MatchStat(
                            id = UUID.randomUUID().toString(),
                            matchParticipantId = UUID.randomUUID().toString(),
                            roundNumber = 1,
                            statType = "GOAL",
                            assistedMatchStatId = null,
                            historyTime = "19:00",
                            createdTime = "2024-03-20T19:00:00"
                        ),
                        MatchStat(
                            id = UUID.randomUUID().toString(),
                            matchParticipantId = UUID.randomUUID().toString(),
                            roundNumber = 1,
                            statType = "ASSIST",
                            assistedMatchStatId = "firstgoalid",
                            historyTime = "19:00",
                            createdTime = "2024-03-20T19:00:00"
                        ),
                    ),listOf(
                        MatchStat(
                            id = UUID.randomUUID().toString(),
                            matchParticipantId = UUID.randomUUID().toString(),
                            roundNumber = 1,
                            statType = "GOAL",
                            assistedMatchStatId = null,
                            historyTime = "19:00",
                            createdTime = "2024-03-20T19:00:00"
                        )
                    ),
                ),
                "B" to listOf(
                    listOf(
                        MatchStat(
                            id = UUID.randomUUID().toString(),
                            matchParticipantId = UUID.randomUUID().toString(),
                            roundNumber = 1,
                            statType = "GOAL",
                            assistedMatchStatId = null,
                            historyTime = "19:00",
                            createdTime = "2024-03-20T19:00:00"
                        )
                    ),
                    listOf(
                        MatchStat(
                            id = UUID.randomUUID().toString(),
                            matchParticipantId = UUID.randomUUID().toString(),
                            roundNumber = 1,
                            statType = "GOAL",
                            assistedMatchStatId = null,
                            historyTime = "19:00",
                            createdTime = "2024-03-20T19:00:00"
                        ),
                        MatchStat(
                            id = UUID.randomUUID().toString(),
                            matchParticipantId = UUID.randomUUID().toString(),
                            roundNumber = 1,
                            statType = "ASSIST",
                            assistedMatchStatId = "firstgoalid",
                            historyTime = "19:00",
                            createdTime = "2024-03-20T19:00:00"
                        ),
                    ),listOf(
                        MatchStat(
                            id = UUID.randomUUID().toString(),
                            matchParticipantId = UUID.randomUUID().toString(),
                            roundNumber = 1,
                            statType = "GOAL",
                            assistedMatchStatId = null,
                            historyTime = "19:00",
                            createdTime = "2024-03-20T19:00:00"
                        )
                    ),
                )
            ),
            "2" to mapOf(
                "A" to listOf(
                    listOf(
                        MatchStat(
                            id = UUID.randomUUID().toString(),
                            matchParticipantId = UUID.randomUUID().toString(),
                            roundNumber = 1,
                            statType = "GOAL",
                            assistedMatchStatId = null,
                            historyTime = "19:00",
                            createdTime = "2024-03-20T19:00:00"
                        ),
                        MatchStat(
                            id = UUID.randomUUID().toString(),
                            matchParticipantId = UUID.randomUUID().toString(),
                            roundNumber = 1,
                            statType = "ASSIST",
                            assistedMatchStatId = "firstgoalid",
                            historyTime = "19:00",
                            createdTime = "2024-03-20T19:00:00"
                        ),
                    ),listOf(
                        MatchStat(
                            id = UUID.randomUUID().toString(),
                            matchParticipantId = UUID.randomUUID().toString(),
                            roundNumber = 1,
                            statType = "GOAL",
                            assistedMatchStatId = null,
                            historyTime = "19:00",
                            createdTime = "2024-03-20T19:00:00"
                        )
                    ), listOf(
                        MatchStat(
                            id = UUID.randomUUID().toString(),
                            matchParticipantId = UUID.randomUUID().toString(),
                            roundNumber = 1,
                            statType = "GOAL",
                            assistedMatchStatId = null,
                            historyTime = "19:00",
                            createdTime = "2024-03-20T19:00:00"
                        )
                    )
                ),
                "B" to listOf(
                    listOf(
                        MatchStat(
                            id = UUID.randomUUID().toString(),
                            matchParticipantId = UUID.randomUUID().toString(),
                            roundNumber = 1,
                            statType = "GOAL",
                            assistedMatchStatId = null,
                            historyTime = "19:00",
                            createdTime = "2024-03-20T19:00:00"
                        )
                    )
                )
            ),
            "3" to mapOf(
                "A" to listOf(
                ),
                "B" to listOf(
                )
            )
        )
        return Response.success(GetMatchStatsResponse(stats))
    }

    override suspend fun createMatchStat(
        accessToken: String,
        request: CreateMatchStatRequest
    ): Response<MatchStat> {
        return Response.success(
            MatchStat(
                id = UUID.randomUUID().toString(),
                matchParticipantId = request.matchParticipantId,
                roundNumber = request.roundNumber,
                statType = request.statType,
                assistedMatchStatId = request.assistedMatchStatId,
                historyTime = "19:00",
                createdTime = "2024-03-20T19:00:00"
            )
        )
    }
} 