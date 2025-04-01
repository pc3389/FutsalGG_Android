package com.futsalgg.app.data.model.request

data class CreateTeamRequest(
    val name: String,
    val introduction: String,
    val rule: String,
    val matchType: String,
    val access: String,
    val dues: Int
) 