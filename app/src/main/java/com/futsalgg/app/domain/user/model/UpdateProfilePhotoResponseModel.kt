package com.futsalgg.app.domain.user.model

/**
 * Update Profile Response
 *
 * @param url 이미지를 볼 수 있는 presigned url
 * @param uri URI
 */
data class UpdateProfilePhotoResponseModel(
    val url: String,
    val uri: String
) 