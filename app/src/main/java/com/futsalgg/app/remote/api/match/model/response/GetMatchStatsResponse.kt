package com.futsalgg.app.remote.api.match.model.response

import com.google.gson.annotations.SerializedName

data class GetMatchStatsResponse(
    @SerializedName("stats")
    val stats: Map<String, Map<String, List<List<MatchStat>>>>
)

data class MatchStat(
    @SerializedName("id")
    val id: String,
    @SerializedName("matchParticipantId")
    val matchParticipantId: String,
    @SerializedName("roundNumber")
    val roundNumber: Int,
    @SerializedName("statType")
    val statType: String,
    @SerializedName("assistedMatchStatId")
    val assistedMatchStatId: String?,
    @SerializedName("historyTime")
    val historyTime: String,
    @SerializedName("createdTime")
    val createdTime: String
) 