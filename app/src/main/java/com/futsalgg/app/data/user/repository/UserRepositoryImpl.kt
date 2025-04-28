package com.futsalgg.app.data.user.repository

import com.futsalgg.app.data.common.mapper.GenderMapper.toDomain
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.file.repository.OkHttpFileUploader
import com.futsalgg.app.domain.common.model.Gender
import com.futsalgg.app.domain.user.model.ProfilePresignedUrlResponseModel
import com.futsalgg.app.domain.user.model.UpdateProfilePhotoResponseModel
import com.futsalgg.app.domain.user.model.User
import com.futsalgg.app.domain.user.repository.UserRepository
import com.futsalgg.app.remote.api.common.ApiResponse
import com.futsalgg.app.remote.api.user.UserApi
import com.futsalgg.app.remote.api.user.model.request.CreateUserRequest
import com.futsalgg.app.remote.api.user.model.request.UpdateNotificationRequest
import com.futsalgg.app.remote.api.user.model.request.UpdateProfilePhotoRequest
import com.futsalgg.app.remote.api.user.model.request.UpdateProfileRequest
import com.google.gson.Gson
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
                    Result.success(body.data.unique)
                } ?: Result.failure(
                    DomainError.ServerError(
                        message = "[isNicknameUnique] 서버 응답이 비어있습니다.",
                        code = response.code()
                    ) as Throwable
                )
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

                Result.failure(
                    DomainError.ServerError(
                        message = "[isNicknameUnique] 서버 오류: ${errorResponse.message}",
                        code = response.code()
                    ) as Throwable
                )
            }
        } catch (e: IOException) {
            Result.failure(
                DomainError.NetworkError(
                    message = "[isNicknameUnique] 네트워크 연결을 확인해주세요.",
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
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

                Result.failure(
                    DomainError.ServerError(
                        message = "[createUser] 서버 오류: ${errorResponse.message}",
                        code = response.code()
                    ) as Throwable
                )
            }
        } catch (e: IOException) {
            Result.failure(
                DomainError.NetworkError(
                    message = "[createUser] 네트워크 연결을 확인해주세요.",
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
                            url = body.data.url,
                            uri = body.data.uri
                        )
                    )
                } ?: Result.failure(
                    DomainError.ServerError(
                        message = "[getProfilePresignedUrl] 서버 응답이 비어있습니다.",
                        code = response.code()
                    ) as Throwable
                )
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

                Result.failure(
                    DomainError.ServerError(
                        message = "[getProfilePresignedUrl] 서버 오류: ${errorResponse.message}",
                        code = response.code()
                    ) as Throwable
                )
            }
        } catch (e: IOException) {
            Result.failure(
                DomainError.NetworkError(
                    message = "[getProfilePresignedUrl] 네트워크 연결을 확인해주세요.",
                    cause = e
                ) as Throwable
            )
        }
    }

    override suspend fun updateProfilePhoto(
        accessToken: String,
        uri: String
    ): Result<UpdateProfilePhotoResponseModel> {
        return try {
            val response = userApi.updateUserProfilePhoto(
                authHeader = "Bearer $accessToken",
                request = UpdateProfilePhotoRequest(uri)
            )
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Result.success(
                        UpdateProfilePhotoResponseModel(
                            url = body.data.url,
                            uri = body.data.uri
                        )

                    )
                } ?: Result.failure(
                    DomainError.ServerError(
                        message = "[updateProfilePhoto] 서버 응답이 비어있습니다.",
                        code = response.code()
                    ) as Throwable
                )
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

                Result.failure(
                    DomainError.ServerError(
                        message = "[updateProfilePhoto] 서버 오류: ${errorResponse.message}",
                        code = response.code()
                    ) as Throwable
                )
            }
        } catch (e: IOException) {
            Result.failure(
                DomainError.NetworkError(
                    message = "[updateProfilePhoto] 네트워크 연결을 확인해주세요.",
                    cause = e
                ) as Throwable
            )
        }
    }

    override suspend fun uploadProfileImage(
        accessToken: String,
        file: File
    ): Result<UpdateProfilePhotoResponseModel> {
        return try {
            // 1. presigned URL 획득
            val presignedResult = getProfilePresignedUrl(accessToken)
            val presignedResponse = presignedResult.getOrElse { return Result.failure(it) }

            // 2. presigned URL로 파일 업로드
            val uploadResult =
                okHttpFileUploader.uploadFileToPresignedUrl(presignedResponse.url, file)
            uploadResult.getOrElse { return Result.failure(it) }

            // 3. 업로드 성공 후, updateProfile API 호출
            updateProfilePhoto(accessToken, presignedResponse.uri)
        } catch (e: Exception) {
            Result.failure(
                DomainError.UnknownError(
                    message = "[uploadProfileImage] 프로필 이미지 업로드 실패: ${e.message}",
                    cause = e
                ) as Throwable
            )
        }
    }

    override suspend fun getMyProfile(accessToken: String): Result<User> = try {
        val response = userApi.getMyProfile(
            accessToken = "Bearer $accessToken"
        )

        if (response.isSuccessful) {
            response.body()?.let { body ->
                Result.success(
                    User(
                        email = body.data.email,
                        name = body.data.name,
                        birthday = body.data.birthday,
                        gender = body.data.gender.toDomain(),
                        squadNumber = body.data.squadNumber,
                        notification = body.data.notification,
                        profileUrl = body.data.profileUrl,
                        createdTime = body.data.createdTime
                    )
                )
            } ?: Result.failure(
                DomainError.ServerError(
                    message = "[getMyProfile] 서버 응답이 비어있습니다.",
                    code = response.code()
                ) as Throwable
            )
        } else {
            val errorBody = response.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

            if (errorResponse.message == "UNAUTHORIZED_TOKEN_AUTHENTICATION_FAILED") {
                Result.failure(
                    DomainError.ServerError(
                        message = "UNAUTHORIZED_TOKEN_AUTHENTICATION_FAILED",
                        code = response.code()
                    )
                )
            } else {
                Result.failure(
                    DomainError.ServerError(
                        message = "[getMyProfile] 서버 오류: ${errorResponse.message}",
                        code = response.code()
                    ) as Throwable
                )
            }
        }
    } catch (e: IOException) {
        Result.failure(
            DomainError.NetworkError(
                message = "[getMyProfile] 네트워크 연결을 확인해주세요.",
                cause = e
            ) as Throwable
        )
    }

    override suspend fun updateNotification(
        accessToken: String,
        notification: Boolean
    ): Result<Unit> = try {
        val response = userApi.updateNotification(
            accessToken = "Bearer $accessToken",
            request = UpdateNotificationRequest(notification)
        )

        if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            val errorBody = response.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

            Result.failure(
                DomainError.ServerError(
                    message = "[updateNotification] 서버 오류: ${errorResponse.message}",
                    code = response.code()
                ) as Throwable
            )
        }
    } catch (e: IOException) {
        Result.failure(
            DomainError.NetworkError(
                message = "[updateNotification] 네트워크 연결을 확인해주세요.",
                cause = e
            ) as Throwable
        )
    }

    override suspend fun updateProfile(
        accessToken: String,
        name: String,
        squadNumber: Int?
    ): Result<User> = try {
        val response = userApi.updateProfile(
            accessToken = "Bearer $accessToken",
            request = UpdateProfileRequest(
                name = name,
                squadNumber = squadNumber
            )
        )

        if (response.isSuccessful) {
            response.body()?.let { body ->
                Result.success(
                    User(
                        email = body.data.email,
                        name = body.data.name,
                        birthday = body.data.birthday,
                        gender = body.data.gender.toDomain(),
                        squadNumber = body.data.squadNumber,
                        profileUrl = body.data.profileUrl,
                        notification = body.data.notification,
                        createdTime = body.data.createdTime
                    )
                )
            } ?: Result.failure(
                DomainError.ServerError(
                    message = "[updateProfile] 서버 응답이 비어있습니다.",
                    code = response.code()
                ) as Throwable
            )
        } else {
            val errorBody = response.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

            Result.failure(
                DomainError.ServerError(
                    message = "[updateProfile] 서버 오류: ${errorResponse.message}",
                    code = response.code()
                ) as Throwable
            )
        }
    } catch (e: IOException) {
        Result.failure(
            DomainError.NetworkError(
                message = "[updateProfile] 네트워크 연결을 확인해주세요.",
                cause = e
            ) as Throwable
        )
    }
} 