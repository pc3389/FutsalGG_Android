package com.futsalgg.app.util

import com.futsalgg.app.presentation.common.state.DateState
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

fun String.dateToRequestFormat(): String {
    val inputFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return try {
        val date = inputFormat.parse(this) ?: "00.00"
        outputFormat.format(date)
    } catch (e: Exception) {
        this
    }
}

fun String.toDateFormat(format: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat(format, Locale.getDefault())
    return try {
        val date = inputFormat.parse(this) ?: "00.00"
        outputFormat.format(date)
    } catch (e: Exception) {
        this
    }
}

fun formatDateWithDayOfWeek(dateString: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = LocalDate.parse(dateString, formatter)
    val dayOfWeek = when (date.dayOfWeek) {
        DayOfWeek.SUNDAY -> "일요일"
        DayOfWeek.MONDAY -> "월요일"
        DayOfWeek.TUESDAY -> "화요일"
        DayOfWeek.WEDNESDAY -> "수요일"
        DayOfWeek.THURSDAY -> "목요일"
        DayOfWeek.FRIDAY -> "금요일"
        DayOfWeek.SATURDAY -> "토요일"
    }
    return dayOfWeek
}

fun String.toTimeRequestFormat(): String {
    return if (this.length == 4) {
        "${this.substring(0, 2)}:${this.substring(2)}"
    } else {
        this
    }
}

fun String.toTimeStringFormat(): String {
    return this.replace(":", "")
}


/**
 * 주어진 날짜 문자열이 지정된 날짜 포맷에 맞고,
 *
 * @param date 검증할 날짜 문자열
 * @param pattern 날짜 포맷 (기본값: "yyyyMMdd"; 필요에 따라 "yyyy-MM-dd" 등으로 변경 가능)
 * @return 조건에 맞으면 true, 아니면 false
 */
fun isValidDate(
    date: String,
    pattern: String = "yyyyMMdd",
    canNotFuture: Boolean = true,
    canNotPast: Boolean = false
): DateState {
    return try {
        val parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern))
        val today = LocalDate.now()
        val isInRangeError = if (canNotFuture) {
            parsedDate.isAfter(today)
        } else if (canNotPast) {
            parsedDate.isBefore(today)
        } else {
            false
        }
        if (isInRangeError) DateState.ErrorNotInRange
        else DateState.Available
    } catch (e: DateTimeParseException) {
        DateState.ErrorStyle
    }
}