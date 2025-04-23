package com.futsalgg.app.domain.user.model

import com.futsalgg.app.domain.common.model.Gender

data class User(
    val email: String,
    val name: String,
    val birthday: String?,
    val gender: Gender,
    val squadNumber: Int?,
    val notification: Boolean,
    val profileUrl: String?,
    val createdTime: String
) 