package com.futsalgg.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.futsalgg.app.R
import com.futsalgg.app.domain.common.model.MatchType
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography
import com.futsalgg.app.util.shadow1
import com.futsalgg.app.util.shadow2
import com.futsalgg.app.util.toPx

@Composable
fun <T : Enum<T>> DropdownBox(
    text: String,
    items: List<T>,
    onItemSelected: (T) -> Unit,
    modifier: Modifier = Modifier
) {
    //TODO 드랍다운 이미 선택된 값은 에메랄드색 배경

    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        // Main dropdown box
        Box(modifier = Modifier.clickable { expanded = true }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .border(
                        width = 1.dp,
                        color = FutsalggColor.mono500,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = text,
                    style = FutsalggTypography.regular_17_200,
                    color = FutsalggColor.mono900,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Icon(
                    painter = painterResource(
                        id = if (expanded) R.drawable.ic_arrow_up_16 else R.drawable.ic_arrow_down_16
                    ),
                    contentDescription = if (expanded) "Close dropdown" else "Open dropdown",
                    tint = FutsalggColor.mono500,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }

        // Dropdown menu
        if (expanded) {
            Popup(
                onDismissRequest = { expanded = false },
                alignment = Alignment.TopStart,
                offset = IntOffset(0, 64.dp.toPx(LocalContext.current).toInt())
            ) {
                Box(
                    Modifier.padding(horizontal = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow1()
                            .shadow2()
                            .clip(RoundedCornerShape(8.dp))
                            .background(FutsalggColor.white)
                            .border(
                                width = 1.dp,
                                color = FutsalggColor.mono100,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Column {
                            items.forEachIndexed { index, item ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            onItemSelected(item)
                                            expanded = false
                                        }
                                ) {
                                    Text(
                                        text = item.toString(),
                                        style = FutsalggTypography.regular_17_200,
                                        color = FutsalggColor.mono900,
                                        modifier = Modifier.padding(16.dp)
                                            .align(Alignment.Center)
                                    )
                                }
                                if (index < items.size - 1) {
                                    HorizontalDivider(
                                        color = FutsalggColor.mono100,
                                        thickness = 1.dp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewDropdownBox() {
    DropdownBox(
        text = "SAMPLE Text",
        items = MatchType.entries,
        onItemSelected = {}
    )
}