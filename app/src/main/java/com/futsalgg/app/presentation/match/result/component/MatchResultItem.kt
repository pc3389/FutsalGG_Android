package com.futsalgg.app.presentation.match.result.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.futsalgg.app.R
import com.futsalgg.app.presentation.common.model.MatchType
import com.futsalgg.app.ui.components.SimpleIconAndText
import com.futsalgg.app.ui.components.SingleButton
import com.futsalgg.app.ui.components.spacers.VerticalSpacer8
import com.futsalgg.app.ui.components.state.IconState
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography
import com.futsalgg.app.util.shadow1
import com.futsalgg.app.util.shadow2
import com.futsalgg.app.util.toPx

@Composable
fun MatchResultItem(
    modifier: Modifier = Modifier,
    matchType: MatchType,
    opponentTeamName: String?,
    location: String,
    matchStartTime: String?,
    matchEndTime: String?,
    buttonEnabled: Boolean = true,
    onResultClick: () -> Unit,
    onScheduleEditClick: () -> Unit,
    onResultEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    val borderColor = if (matchType == MatchType.INTER_TEAM) {
        FutsalggColor.blue100
    } else {
        FutsalggColor.mint100
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow1()
            .shadow2(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = FutsalggColor.white
        ),
        border = BorderStroke(
            width = 1.dp,
            color = borderColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (matchType == MatchType.INTER_TEAM) {
                            FutsalggColor.blue50
                        } else {
                            FutsalggColor.mint50
                        }
                    )
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .weight(1f),
                    text = matchType.displayName,
                    style = FutsalggTypography.bold_20_300
                )
                IconButton(
                    onClick = {
                        showMenu = true
                    }
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_meatball_menu),
                        contentDescription = ""
                    )
                }
                if (showMenu) {
                    val context = LocalContext.current
                    Popup(
                        onDismissRequest = { showMenu = false },
                        offset = IntOffset(
                            x = 52.dp.toPx(context).toInt(),
                            y = 48.dp.toPx(context).toInt()
                        )
                    ) {
                        @Composable
                        fun DropdownMenuBox(text: String, onClick: () -> Unit) {
                            Box(
                                modifier = Modifier
                                    .clickable {
                                        onClick()
                                    }
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(
                                            vertical = 12.dp,
                                            horizontal = 16.dp
                                        )
                                        .fillMaxWidth(),
                                    text = text,
                                    style = FutsalggTypography.regular_17_200,
                                    color = FutsalggColor.mono900,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                        Card(
                            modifier = modifier
                                .width(174.dp)
                                .shadow1()
                                .shadow2(),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = FutsalggColor.white
                            ),
                            border = BorderStroke(
                                width = 1.dp,
                                color = FutsalggColor.mono200
                            )
                        ) {
                        Column(
                            modifier = Modifier,
//                                .clip(RoundedCornerShape(8.dp))
//                                .border(
//                                    1.dp,
//                                    FutsalggColor.mono200,
//                                    shape = RoundedCornerShape(8.dp)
//                                )
//                                .background(
//                                    color = FutsalggColor.white,
//                                    shape = RoundedCornerShape(8.dp)
//                                )
//                                .width(174.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            DropdownMenuBox(
                                text = stringResource(R.string.match_result_menu_schedule_update_text),
                                onClick = {
                                    onScheduleEditClick()
                                    showMenu = false
                                }
                            )

                            HorizontalDivider(
                                thickness = 1.dp,
                                color = FutsalggColor.mono200
                            )
                            DropdownMenuBox(
                                text = stringResource(R.string.match_result_menu_result_update_text),
                                onClick = {
                                    onResultEditClick()
                                    showMenu = false
                                }
                            )
                            HorizontalDivider(
                                thickness = 1.dp,
                                color = FutsalggColor.mono200
                            )
                            DropdownMenuBox(
                                text = stringResource(R.string.delete_text),
                                onClick = {
                                    onDeleteClick()
                                    showMenu = false
                                }
                            )
                        }
                    }
                    }
                }
            }
            HorizontalDivider(
                thickness = 1.dp,
                color = borderColor
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                opponentTeamName?.let {
                    SimpleIconAndText(
                        modifier = Modifier.padding(vertical = 8.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_vs_20),
                        text = it
                    )
                }

                SimpleIconAndText(
                    modifier = Modifier.padding(vertical = 8.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_location_20),
                    text = location
                )

                SimpleIconAndText(
                    modifier = Modifier.padding(vertical = 8.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_clock_20),
                    text = "${matchStartTime ?: "00:00"} ~ ${matchEndTime ?: "00:00"}"
                )

                VerticalSpacer8()

                if (buttonEnabled) {
                    SingleButton(
                        onClick = onResultClick,
                        modifier = Modifier.padding(vertical = 12.dp),
                        text = "경기 결과 확인하기",
                        containerColor = FutsalggColor.white,
                        contentColor = FutsalggColor.mono900,
                        hasBorder = true,
                        iconState = IconState()
                    )
                }
            }
        }
    }
}