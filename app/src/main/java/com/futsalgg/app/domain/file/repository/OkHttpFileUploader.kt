package com.futsalgg.app.domain.file.repository

import java.io.File

interface OkHttpFileUploader {
    suspend fun uploadFileToPresignedUrl(presignedUrl: String, file: File): Result<Unit>
} 