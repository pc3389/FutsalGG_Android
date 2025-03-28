package com.futsalgg.app.ui.components.calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.futsalgg.app.R
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun CalendarTopBar(
    currentMonth: YearMonth,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onMonthTitleClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = onPreviousMonth) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    R.drawable.ic_arrow_backward_small
                ), contentDescription = "Previous Month"
            )
        }
        Text(
            text = currentMonth.format(DateTimeFormatter.ofPattern("yyyy년 M월")),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onMonthTitleClick() }
        )
        IconButton(onClick = onNextMonth) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    R.drawable.ic_arrow_forward_small
                ), contentDescription = "Next Month"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalendarTopBar() {
    CalendarTopBar(
        currentMonth = YearMonth.now(),
        onPreviousMonth = {},
        onNextMonth = {},
        onMonthTitleClick = {}
    )
}