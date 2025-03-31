package com.futsalgg.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.futsalgg.app.R
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun CheckableTextBox(
    text: String,
    isChecked: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = if (isChecked) FutsalggColor.mono900 else FutsalggColor.mono200
    val contentColor = if (isChecked) FutsalggColor.mono900 else FutsalggColor.mono500
    val iconRes = if (isChecked) {
        R.drawable.ic_checkbox_true_24
    } else {
        R.drawable.ic_checkbox_false_24
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, borderColor, shape = RoundedCornerShape(8.dp))
            .clickable { onToggle() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = text,
            color = contentColor,
            style = FutsalggTypography.regular_17_200
        )
    }
}