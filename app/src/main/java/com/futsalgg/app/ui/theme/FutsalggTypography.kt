package com.futsalgg.app.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

object FutsalggTypography {
    val smallSize = 15.sp
    val mediumSize = 17.sp
    val largeSize = 20.sp
    val xLargeSize = 25.sp
    val xxLargeSize = 40.sp
    val light_15_100 = TextStyle(
        fontSize = smallSize,
        lineHeight = lineHeightPercent(smallSize, 140),
        fontWeight = FontWeight.Light,
        fontFamily = FutsalggFont.pretendard
    )
    val light_17_200 = TextStyle(
        fontSize = mediumSize,
        lineHeight = lineHeightPercent(mediumSize, 140),
        fontWeight = FontWeight.Light,
        fontFamily = FutsalggFont.pretendard
    )
    val light_20_300 = TextStyle(
        fontSize = largeSize,
        lineHeight = lineHeightPercent(largeSize, 140),
        fontWeight = FontWeight.Light,
        fontFamily = FutsalggFont.pretendard
    )
    val regular_15_100 = TextStyle(
        fontSize = smallSize,
        lineHeight = lineHeightPercent(smallSize, 150),
        fontWeight = FontWeight.Normal,
        fontFamily = FutsalggFont.pretendard
    )
    val regular_17_200 = TextStyle(
        fontSize = mediumSize,
        lineHeight = lineHeightPercent(mediumSize, 150),
        fontWeight = FontWeight.Normal,
        fontFamily = FutsalggFont.pretendard
    )
    val regular_20_300 = TextStyle(
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
    val bold_15_100 = TextStyle(
        fontSize = smallSize,
        lineHeight = lineHeightPercent(smallSize, 150),
        fontWeight = FontWeight.Bold,
        fontFamily = FutsalggFont.pretendard
    )
    val bold_17_200 = TextStyle(
        fontSize = mediumSize,
        lineHeight = lineHeightPercent(mediumSize, 150),
        fontWeight = FontWeight.Bold,
        fontFamily = FutsalggFont.pretendard
    )
    val bold_20_300 = TextStyle(
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