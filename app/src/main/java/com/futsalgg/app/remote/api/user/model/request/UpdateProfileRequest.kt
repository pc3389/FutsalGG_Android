package com.futsalgg.app.remote.api.user.model.request

/**
 * @param uri GET/profile-presigned-url에서 받아온 uri
 */
data class UpdateProfileRequest(
    val uri: String
)