package com.futsalgg.app.domain.repository

import java.io.File

interface FileUploader {
    suspend fun uploadFileToPresignedUrl(presignedUrl: String, file: File): Result<Unit>
}