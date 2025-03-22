package com.example.futsalgg_android.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

object FutsalggTypography {
    val smallSize = 14.sp
    val mediumSize = 16.sp
    val largeSize = 18.sp
    val xLargeSize = 24.sp
    val xxLargeSize = 40.sp
    val light_14_100 = TextStyle(
        fontSize = smallSize,
        lineHeight = lineHeightPercent(smallSize, 140),
        fontWeight = FontWeight.Light,
        fontFamily = FutsalggFont.pretendard
    )
    val light_16_200 = TextStyle(
        fontSize = mediumSize,
        lineHeight = lineHeightPercent(mediumSize, 140),
        fontWeight = FontWeight.Light,
        fontFamily = FutsalggFont.pretendard
    )
    val light_18_300 = TextStyle(
        fontSize = largeSize,
        lineHeight = lineHeightPercent(largeSize, 140),
        fontWeight = FontWeight.Light,
        fontFamily = FutsalggFont.pretendard
    )
    val regular_14_100 = TextStyle(
        fontSize = smallSize,
        lineHeight = lineHeightPercent(smallSize, 150),
        fontWeight = FontWeight.Normal,
        fontFamily = FutsalggFont.pretendard
    )
    val regular_16_200 = TextStyle(
        fontSize = mediumSize,
        lineHeight = lineHeightPercent(mediumSize, 150),
        fontWeight = FontWeight.Normal,
        fontFamily = FutsalggFont.pretendard
    )
    val regular_18_300 = TextStyle(
        fontSize = largeSize,
        lineHeight = lineHeightPercent(largeSize, 150),
        fontWeight = FontWeight.Normal,
        fontFamily = FutsalggFont.pretendard
    )
    val regular_24_400 = TextStyle(
        fontSize = xLargeSize,
        lineHeight = lineHeightPercent(xLargeSize, 150),
        fontWeight = FontWeight.Normal,
        fontFamily = FutsalggFont.pretendard
    )
    val bold_14_100 = TextStyle(
        fontSize = smallSize,
        lineHeight = lineHeightPercent(smallSize, 150),
        fontWeight = FontWeight.Bold,
        fontFamily = FutsalggFont.pretendard
    )
    val bold_16_200 = TextStyle(
        fontSize = mediumSize,
        lineHeight = lineHeightPercent(mediumSize, 150),
        fontWeight = FontWeight.Bold,
        fontFamily = FutsalggFont.pretendard
    )
    val bold_18_300 = TextStyle(
        fontSize = largeSize,
        lineHeight = lineHeightPercent(largeSize, 150),
        fontWeight = FontWeight.Bold,
        fontFamily = FutsalggFont.pretendard
    )
    val bold_24_400 = TextStyle(
        fontSize = xLargeSize,
        lineHeight = lineHeightPercent(xLargeSize, 150),
        fontWeight = FontWeight.Bold,
        fontFamily = FutsalggFont.pretendard
    )
    val bold_40_500 = TextStyle(
        fontSize = xxLargeSize,
        lineHeight = lineHeightPercent(xxLargeSize, 150),
        fontWeight = FontWeight.Bold,
        fontFamily = FutsalggFont.pretendard
    )

    private fun lineHeightPercent(fontSize: TextUnit, percent: Int): TextUnit {
        return (fontSize.value * percent / 100).sp
    }
}