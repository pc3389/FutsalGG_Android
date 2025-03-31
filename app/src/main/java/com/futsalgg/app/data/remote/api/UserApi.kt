package com.futsalgg.app.data.remote.api

import com.futsalgg.app.data.model.response.CheckNicknameResponse
import com.futsalgg.app.data.model.request.CreateUserRequest
import com.futsalgg.app.data.model.request.UpdateProfileRequest
import com.futsalgg.app.data.model.response.ProfilePresignedUrlResponse
import com.futsalgg.app.data.model.response.UpdateProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Query

interface UserApi {
    // 닉네임 중복을 확인하는 API
    @GET("/users/check-nickname")
    suspend fun checkNickname(
        @Query("nickname") nickname: String
    ): Response<CheckNicknameResponse>

    // 유저의 정보를 등록하는 API
    @PATCH("/users")
    suspend fun createUser(
        @Header("Authorization") authHeader: String,
        @Body request: CreateUserRequest
    ): Response<Void>

    // 프로필 사진 업로드를 위해 presigned url을 가져오는 API
    @GET("/users/profile-presigned-url")
    suspend fun getProfilePresignedUrl(
        @Header("Authorization") authHeader: String
    ): Response<ProfilePresignedUrlResponse>

    // [GET] /user/profile-presigned-url로 가져온 다음 업로드가 성공했을 시 profile을 업데이트 하는 API 호출
    @PATCH("/users/profile")
    suspend fun updateUserProfile(
        @Header("Authorization") authHeader: String,
        @Body request: UpdateProfileRequest
    ): Response<UpdateProfileResponse>
}