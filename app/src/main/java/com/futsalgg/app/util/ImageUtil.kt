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

fun cropImageFromUri(
    context: Context,
    uri: Uri,
    scale: Float,
    offset: Offset,
    rotation: Float,
    cropSize: Dp,
    onCropped: (Bitmap) -> Unit
) {
    val resolver = context.contentResolver
    val inputStream = resolver.openInputStream(uri) ?: return
    val originalBitmap = BitmapFactory.decodeStream(inputStream)

    val cropSizePx = cropSize.toPx(context)

    val matrix = Matrix().apply {
        postScale(scale, scale)
        postRotate(rotation)
        postTranslate(offset.x, offset.y)
    }

    val transformedBitmap = Bitmap.createBitmap(
        originalBitmap,
        0,
        0,
        originalBitmap.width,
        originalBitmap.height,
        matrix,
        true
    )

    val centerX = transformedBitmap.width / 2
    val centerY = transformedBitmap.height / 2

    val radius = (cropSizePx / 2).toInt()
    val croppedBitmap = Bitmap.createBitmap(cropSizePx.toInt(), cropSizePx.toInt(), Bitmap.Config.ARGB_8888)

    val canvas = Canvas(croppedBitmap)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val path = Path().apply {
        addCircle(cropSizePx / 2, cropSizePx / 2, cropSizePx / 2, Path.Direction.CCW)
    }
    canvas.clipPath(path)
    canvas.drawBitmap(
        transformedBitmap,
        Rect(centerX - radius, centerY - radius, centerX + radius, centerY + radius),
        Rect(0, 0, cropSizePx.toInt(), cropSizePx.toInt()),
        paint
    )

    onCropped(croppedBitmap)
}

fun Dp.toPx(context: Context): Float {
    return this.value * context.resources.displayMetrics.density
}