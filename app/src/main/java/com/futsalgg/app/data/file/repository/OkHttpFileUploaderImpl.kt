package com.futsalgg.app.data.file.repository

import com.futsalgg.app.domain.file.repository.OkHttpFileUploader
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import com.futsalgg.app.domain.common.error.DomainError
import javax.inject.Inject

class OkHttpFileUploaderImpl @Inject constructor(
    private val client: OkHttpClient
) : OkHttpFileUploader {

    override suspend fun uploadFileToPresignedUrl(presignedUrl: String, file: File): Result<Unit> {
        return try {
            val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                    "file",
                    file.name,
                    file.asRequestBody("application/octet-stream".toMediaType())
                )
                .build()

            val request = Request.Builder()
                .url(presignedUrl)
                .put(requestBody)
                .build()

            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(
                    DomainError.ServerError(
                        code = response.code,
                        message = "파일 업로드 실패: ${response.code}"
                    )
                )
            }
        } catch (e: IOException) {
            Result.failure(
                DomainError.NetworkError(
                    message = "네트워크 연결을 확인해주세요.",
                    cause = e
                )
            )
        } catch (e: Exception) {
            Result.failure(
                DomainError.UnknownError(
                    message = "알 수 없는 오류가 발생했습니다.",
                    cause = e
                )
            )
        }
    }
} 