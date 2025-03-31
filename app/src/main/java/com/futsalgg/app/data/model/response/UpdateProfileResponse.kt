package com.futsalgg.app.data.model.response

/**
 * Update Profile Response
 *
 * @param url 이미지를 볼 수 있는 presigned url
 * @param uri URI
 */
data class UpdateProfileResponse(
    val url: String,
    val uri: String
)