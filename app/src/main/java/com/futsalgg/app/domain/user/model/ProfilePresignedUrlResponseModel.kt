package com.futsalgg.app.domain.user.model

/**
 * Response
 *
 * @param url Naver Cloud 에 파일을 업로드할 수 있는 URL.
 * @param uri 파일이 저장된 uri
 */
data class ProfilePresignedUrlResponseModel(
    val url: String,
    val uri: String
) 