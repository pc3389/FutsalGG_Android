package com.futsalgg.app.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.net.Uri
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import java.io.File
import java.io.FileOutputStream

fun cropImageFromUri(
    context: Context,
    uri: Uri,
    scale: Float,
    offset: Offset,  // UI에서 사용자가 드래그한 offset 값
    rotation: Float,
    cropSize: Dp, // CropOverlay의 visibleSize (예: 260.dp)
    onCropped: (Bitmap) -> Unit
) {
    val resolver = context.contentResolver
    val inputStream = resolver.openInputStream(uri) ?: return
    val originalBitmap = BitmapFactory.decodeStream(inputStream)

    val cropSizePx = cropSize.toPx(context)

    // Matrix에 scale과 rotation만 적용(translation은 별도로 반영)
    val matrix = Matrix().apply {
        postScale(scale, scale)
        postRotate(rotation)
    }

    // 변환된 비트맵 (scale과 rotation이 적용됨)
    val transformedBitmap = Bitmap.createBitmap(
        originalBitmap,
        0,
        0,
        originalBitmap.width,
        originalBitmap.height,
        matrix,
        true
    )

    // UI에서 이미지는 Box의 중앙에 고정되고, translation(offset)이 적용되어 있습니다.
    // 즉, 이미지의 본래 중앙은 화면 중앙이었지만, offset 만큼 이동하여
    // 화면 중앙에 보이는 부분은 transformedBitmap의 중앙에서 offset을 뺀 위치가 됩니다.
    val imageCenterX = transformedBitmap.width / 2
    val imageCenterY = transformedBitmap.height / 2

    // 사용자가 드래그한 offset을 반영하여, 실제 화면 중앙에 해당하는 transformedBitmap상의 좌표를 계산합니다.
    // (offset이 양수이면 이미지가 오른쪽으로 이동했으므로, 화면 중앙은 이미지 중앙보다 왼쪽에 해당)
    val cropCenterX = imageCenterX - offset.x.toInt()
    val cropCenterY = imageCenterY - offset.y.toInt()

    val halfCrop = (cropSizePx / 2).toInt()
    val safeCropLeft = (cropCenterX - halfCrop).coerceAtLeast(0)
    val safeCropTop = (cropCenterY - halfCrop).coerceAtLeast(0)
    val safeCropRight = (cropCenterX + halfCrop).coerceAtMost(transformedBitmap.width)
    val safeCropBottom = (cropCenterY + halfCrop).coerceAtMost(transformedBitmap.height)

    val cropWidth = safeCropRight - safeCropLeft
    val cropHeight = safeCropBottom - safeCropTop

    if (cropWidth <= 0 || cropHeight <= 0) {
        onCropped(transformedBitmap)
        return
    }

    val croppedBitmap = Bitmap.createBitmap(transformedBitmap, safeCropLeft, safeCropTop, cropWidth, cropHeight)

    // 만약 원형 크롭이 필요하다면 원형 마스크 적용
    val finalBitmap = Bitmap.createBitmap(croppedBitmap.width, croppedBitmap.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(finalBitmap)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val path = Path().apply {
        addCircle(
            croppedBitmap.width / 2f,
            croppedBitmap.height / 2f,
            croppedBitmap.width.coerceAtMost(croppedBitmap.height) / 2f,
            Path.Direction.CCW
        )
    }
    canvas.clipPath(path)
    canvas.drawBitmap(croppedBitmap, 0f, 0f, paint)

    onCropped(finalBitmap)
}

fun Dp.toPx(context: Context): Float {
    return this.value * context.resources.displayMetrics.density
}

/**
 * Bitmap을 JPEG 형식의 File로 변환합니다.
 * @param context 파일을 저장할 Context (예: applicationContext)
 * @param fileName 생성할 파일 이름 (예: "profile.jpg")
 */
fun Bitmap.toFile(context: Context, fileName: String): File {
    // 캐시 디렉토리 또는 임시 저장소에 파일 생성
    val file = File(context.cacheDir, fileName)
    file.createNewFile()

    // FileOutputStream을 통해 Bitmap을 파일에 기록합니다.
    FileOutputStream(file).use { out ->
        this.compress(Bitmap.CompressFormat.JPEG, 100, out)
        out.flush()
    }
    return file
}