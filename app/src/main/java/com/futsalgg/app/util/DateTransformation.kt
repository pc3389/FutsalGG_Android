package com.futsalgg.app.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.time.LocalDate

class DateTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return dateFilter(text)
    }
}

fun dateFilter(text: AnnotatedString): TransformedText {
    val trimmed = text.text.take(8)
    val out = buildString {
        trimmed.forEachIndexed { index, c ->
            append(c)
            if (index == 3 || index == 5) append("-")
        }
    }

    val offsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return when {
                offset <= 4 -> offset
                offset <= 6 -> offset + 1
                offset <= 8 -> offset + 2
                else -> out.length
            }
        }

        override fun transformedToOriginal(offset: Int): Int {
            return when {
                offset <= 4 -> offset
                offset <= 7 -> offset - 1
                offset <= 10 -> offset - 2
                else -> 8
            }
        }
    }

    return TransformedText(AnnotatedString(out), offsetTranslator)
}

fun String.toLocalDateOrNull(): LocalDate? {
    return try {
        LocalDate.parse(this)
    } catch (e: Exception) {
        null
    }
}