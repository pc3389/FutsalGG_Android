package com.futsalgg.app.presentation.user.createuser.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.futsalgg.app.ui.components.CheckableTextBox
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography
import androidx.compose.material3.Text

@Composable
fun NotificationUi(
    isChecked: Boolean,
    onToggle: () -> Unit
) {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(
            text = "푸시 알림",
            style = FutsalggTypography.bold_20_300,
            color = FutsalggColor.mono900
        )

        Spacer(Modifier.width(4.dp))

        Text(
            text = "(경기 일정, 투표 등록)",
            style = FutsalggTypography.regular_17_200,
            color = FutsalggColor.mono400
        )
    }

    Spacer(Modifier.height(8.dp))

    CheckableTextBox(
        text = "알림 받기",
        isChecked = isChecked,
        onToggle = onToggle
    )
}