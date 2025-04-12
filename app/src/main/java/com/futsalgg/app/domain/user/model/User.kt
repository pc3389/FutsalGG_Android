package com.futsalgg.app.domain.user.model

data class User(
    val email: String,
    val name: String,
    val squadNumber: Int?,
    val notification: Boolean,
    val profileUrl: String?,
    val createdTime: String
) 