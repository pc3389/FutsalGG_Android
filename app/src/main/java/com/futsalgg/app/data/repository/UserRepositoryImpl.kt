package com.futsalgg.app.data.repository

import com.futsalgg.app.data.model.request.CreateUserRequest
import com.futsalgg.app.data.model.request.UpdateProfileRequest
import com.futsalgg.app.data.model.response.ProfilePresignedUrlResponse
import com.futsalgg.app.data.model.response.UpdateProfileResponse
import com.futsalgg.app.data.remote.api.UserApi
import com.futsalgg.app.domain.model.Gender
import com.futsalgg.app.domain.repository.FileUploader
import com.futsalgg.app.domain.repository.UserRepository
import java.io.File
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val fileUploader: FileUploader
) : UserRepository {

    override suspend fun isNicknameUnique(nickname: String): Result<Boolean> {
        return try {
            val response = userApi.checkNickname(nickname)
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Result.success(body.unique)
                } ?: Result.failure(Throwable("서버 응답이 비어있습니다."))
            } else {
                Result.failure(Throwable("서버 오류: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(Throwable("네트워크 오류: ${e.message}"))
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
                Result.failure(Throwable("회원가입 실패: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(Throwable("네트워크 오류: ${e.message}"))
        }
    }

    override suspend fun getProfilePresignedUrl(accessToken: String): Result<ProfilePresignedUrlResponse> {
        return try {
            val response = userApi.getProfilePresignedUrl("Bearer $accessToken")
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Result.success(body)
                } ?: Result.failure(Throwable("서버 응답이 비어있습니다."))
            } else {
                Result.failure(Throwable("서버 오류: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(Throwable("네트워크 오류: ${e.message}"))
        }
    }

    override suspend fun updateProfile(
        accessToken: String,
        uri: String
    ): Result<UpdateProfileResponse> {
        return try {
            val response = userApi.updateUserProfile(
                authHeader = "Bearer $accessToken",
                request = UpdateProfileRequest(uri)
            )
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Result.success(body)
                } ?: Result.failure(Throwable("서버 응답이 비어있습니다."))
            } else {
                Result.failure(Throwable("서버 오류: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(Throwable("네트워크 오류: ${e.message}"))
        }
    }

    override suspend fun uploadProfileImage(accessToken: String, file: File): Result<UpdateProfileResponse> {
        return try {
            // 1. presigned URL 획득
            val presignedResult = getProfilePresignedUrl(accessToken)
            val presignedResponse = presignedResult.getOrElse { return Result.failure(it) }

            // 2. presigned URL로 파일 업로드
            val uploadResult = fileUploader.uploadFileToPresignedUrl(presignedResponse.url, file)
            uploadResult.getOrElse { return Result.failure(it) }

            // 3. 업로드 성공 후, updateProfile API 호출
            updateProfile(accessToken, presignedResponse.uri)
        } catch (e: Exception) {
            Result.failure(Throwable("프로필 이미지 업로드 실패: ${e.message}"))
        }
    }
}