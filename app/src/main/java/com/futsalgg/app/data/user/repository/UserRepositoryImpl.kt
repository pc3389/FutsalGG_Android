package com.futsalgg.app.data.user.repository

import com.futsalgg.app.remote.api.user.model.request.CreateUserRequest
import com.futsalgg.app.remote.api.user.model.request.UpdateProfileRequest
import com.futsalgg.app.domain.file.repository.OkHttpFileUploader
import com.futsalgg.app.remote.api.user.UserApi
import com.futsalgg.app.domain.user.model.Gender
import com.futsalgg.app.domain.user.model.ProfilePresignedUrlResponseModel
import com.futsalgg.app.domain.user.model.UpdateProfileResponseModel
import com.futsalgg.app.domain.user.repository.UserRepository
import com.futsalgg.app.data.common.error.DataError
import java.io.File
import java.io.IOException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val okHttpFileUploader: OkHttpFileUploader
) : UserRepository {

    override suspend fun isNicknameUnique(nickname: String): Result<Boolean> {
        return try {
            val response = userApi.checkNickname(nickname)
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Result.success(body.unique)
                } ?: Result.failure(
                    DataError.ServerError(
                        message = "서버 응답이 비어있습니다.",
                        cause = null
                    ) as Throwable
                )
            } else {
                Result.failure(
                    DataError.ServerError(
                        message = "서버 오류: ${response.code()}",
                        cause = null
                    ) as Throwable
                )
            }
        } catch (e: IOException) {
            Result.failure(
                DataError.NetworkError(
                    message = "네트워크 연결을 확인해주세요.",
                    cause = e
                ) as Throwable
            )
        }
    }

    override suspend fun createUser(
        accessToken: String,
        nickname: String,
        birthDate: String,
        gender: Gender,
        agreement: Boolean,
        notification: Boolean
    ): Result<Unit> {
        return try {
            val response = userApi.createUser(
                authHeader = "Bearer $accessToken",
                request = CreateUserRequest(
                    nickname = nickname,
                    birthDate = birthDate,
                    gender = gender.name,
                    agreement = agreement,
                    notification = notification
                )
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(
                    DataError.ServerError(
                        message = "회원가입 실패: ${response.code()}",
                        cause = null
                    ) as Throwable
                )
            }
        } catch (e: IOException) {
            Result.failure(
                DataError.NetworkError(
                    message = "네트워크 연결을 확인해주세요.",
                    cause = e
                ) as Throwable
            )
        }
    }

    override suspend fun getProfilePresignedUrl(accessToken: String): Result<ProfilePresignedUrlResponseModel> {
        return try {
            val response = userApi.getProfilePresignedUrl("Bearer $accessToken")
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Result.success(
                        ProfilePresignedUrlResponseModel(
                            url = body.url,
                            uri = body.uri
                        )
                    )
                } ?: Result.failure(
                    DataError.ServerError(
                        message = "서버 응답이 비어있습니다.",
                        cause = null
                    ) as Throwable
                )
            } else {
                Result.failure(
                    DataError.ServerError(
                        message = "서버 오류: ${response.code()}",
                        cause = null
                    ) as Throwable
                )
            }
        } catch (e: IOException) {
            Result.failure(
                DataError.NetworkError(
                    message = "네트워크 연결을 확인해주세요.",
                    cause = e
                ) as Throwable
            )
        }
    }

    override suspend fun updateProfile(
        accessToken: String,
        uri: String
    ): Result<UpdateProfileResponseModel> {
        return try {
            val response = userApi.updateUserProfile(
                authHeader = "Bearer $accessToken",
                request = UpdateProfileRequest(uri)
            )
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Result.success(
                        UpdateProfileResponseModel(
                            url = body.url,
                            uri = body.uri
                        )
                    )
                } ?: Result.failure(
                    DataError.ServerError(
                        message = "서버 응답이 비어있습니다.",
                        cause = null
                    ) as Throwable
                )
            } else {
                Result.failure(
                    DataError.ServerError(
                        message = "서버 오류: ${response.code()}",
                        cause = null
                    ) as Throwable
                )
            }
        } catch (e: IOException) {
            Result.failure(
                DataError.NetworkError(
                    message = "네트워크 연결을 확인해주세요.",
                    cause = e
                ) as Throwable
            )
        }
    }

    override suspend fun uploadProfileImage(
        accessToken: String,
        file: File
    ): Result<UpdateProfileResponseModel> {
        return try {
            // 1. presigned URL 획득
            val presignedResult = getProfilePresignedUrl(accessToken)
            val presignedResponse = presignedResult.getOrElse { return Result.failure(it) }

            // 2. presigned URL로 파일 업로드
            val uploadResult =
                okHttpFileUploader.uploadFileToPresignedUrl(presignedResponse.url, file)
            uploadResult.getOrElse { return Result.failure(it) }

            // 3. 업로드 성공 후, updateProfile API 호출
            updateProfile(accessToken, presignedResponse.uri)
        } catch (e: Exception) {
            Result.failure(
                DataError.UnknownError(
                    message = "프로필 이미지 업로드 실패: ${e.message}",
                    cause = e
                ) as Throwable
            )
        }
    }
} 