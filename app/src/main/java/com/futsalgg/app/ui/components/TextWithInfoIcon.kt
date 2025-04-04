package com.futsalgg.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.futsalgg.app.R
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun TextWithInfoIcon(
    text: String? = null,
    textWithStar: String? = null,
    info: String,
    modifier: Modifier = Modifier
) {
    var showInfo by remember { mutableStateOf(false) }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (text != null) {
            Text(
                text = text,
                style = FutsalggTypography.bold_20_300
            )
        }
        if (textWithStar != null) {
            TextWithStar(textWithStar)
        }

        Box {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_info_22),
                contentDescription = "info",
                modifier = Modifier
                    .padding(start = 8.dp, top = 3.dp, bottom = 3.dp)
                    .size(16.dp)
                    .clickable { showInfo = !showInfo }
            )

            DropdownMenu(
                expanded = showInfo,
                onDismissRequest = { showInfo = false },
                modifier = Modifier
                    .border(
                        1.dp,
                        FutsalggColor.mono200
                    )
                    .background(
                        color = FutsalggColor.white,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Text(
                    text = info,
                    style = FutsalggTypography.regular_17_200,
                    color = FutsalggColor.mono900,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}