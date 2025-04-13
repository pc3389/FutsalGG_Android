package com.futsalgg.app.remote.api.user.model.response

/**
 * Update Profile Response
 *
 * @param url 이미지를 볼 수 있는 presigned url
 * @param uri URI
 */
data class UpdateProfilePhotoResponse(
    val url: String,
    val uri: String
) 