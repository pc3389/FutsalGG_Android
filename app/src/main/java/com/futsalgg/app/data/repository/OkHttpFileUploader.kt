package com.futsalgg.app.data.repository

import com.futsalgg.app.domain.repository.FileUploader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class OkHttpFileUploader @Inject constructor(
    private val client: OkHttpClient
) : FileUploader {
    override suspend fun uploadFileToPresignedUrl(presignedUrl: String, file: File): Result<Unit> =
        withContext(
            Dispatchers.IO
        ) {
            try {
                val mediaType = "image/jpeg".toMediaTypeOrNull()
                val fileBytes = file.readBytes()
                val requestBody = fileBytes.toRequestBody(mediaType)
                val request = Request.Builder()
                    .url(presignedUrl)
                    .put(requestBody)
                    .build()
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    Result.success(Unit)
                } else {
                    Result.failure(Throwable("업로드 실패: ${response.code}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}