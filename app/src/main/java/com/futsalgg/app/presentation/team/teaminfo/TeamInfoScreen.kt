package com.futsalgg.app.presentation.team.teaminfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.futsalgg.app.R
import com.futsalgg.app.navigation.RoutePath
import com.futsalgg.app.presentation.common.model.MatchType
import com.futsalgg.app.presentation.common.screen.BaseScreen
import com.futsalgg.app.ui.components.spacers.VerticalSpacer12
import com.futsalgg.app.ui.components.spacers.VerticalSpacer16
import com.futsalgg.app.ui.components.spacers.VerticalSpacer4
import com.futsalgg.app.ui.components.spacers.VerticalSpacer8
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography
import com.futsalgg.app.util.shadow1
import com.futsalgg.app.util.shadow2

@Composable
fun TeamInfoScreen(
    navController: NavController,
    viewModel: TeamInfoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val teamMembersState by viewModel.teamMembersState.collectAsState()
    val teamState by viewModel.teamState.collectAsState()

    BaseScreen(
        navController = navController,
        uiState = uiState,
        title = stringResource(R.string.team_info_title),
        screenName = RoutePath.TEAM_INFO,
        rightText = stringResource(R.string.team_leave_text),
        onRightClick = {
            // TODO Team 탈퇴
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .background(FutsalggColor.white)
                    .padding(horizontal = 16.dp)
            ) {
                VerticalSpacer16()
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 아이콘
                    AsyncImage(
                        model = teamState.logoUrl,
                        contentDescription = "프로필 이미지",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape),
                        placeholder = painterResource(R.drawable.ic_team_default_56),
                        error = painterResource(R.drawable.ic_team_default_56)
                    )
                    Spacer(Modifier.width(24.dp))

                    Column {
                        // 팀명
                        Text(
                            text = teamState.name,
                            style = FutsalggTypography.bold_20_300,
                            color = FutsalggColor.mono900
                        )
                        VerticalSpacer12()
                        @Composable
                        fun colorTextBox(
                            text: String,
                            backgroundColor: Color = FutsalggColor.mint50,
                            borderColor: Color = FutsalggColor.mint300,
                            textColor: Color = FutsalggColor.mint500
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(backgroundColor)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(
                                        width = 1.dp,
                                        color = borderColor,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            ) {
                                Text(
                                    modifier = Modifier.padding(
                                        vertical = 4.dp,
                                        horizontal = 8.dp
                                    ),
                                    text = text,
                                    style = FutsalggTypography.regular_15_100,
                                    color = textColor
                                )
                            }
                        }
                        Row {
                            colorTextBox(
                                text = MatchType.INTRA_SQUAD.directName
                            )
                            // TODO MVP 2
//                            Spacer(Modifier.width(8.dp))
//                            colorTextBox(
//                                text = MatchType.INTER_TEAM.directName,
//                                backgroundColor = FutsalggColor.blue50,
//                                borderColor = FutsalggColor.blue200,
//                                textColor = FutsalggColor.blue500
//                            )
                        }
                    }
                }
                VerticalSpacer16()
                HorizontalDivider(
                    thickness = 1.dp,
                    color = FutsalggColor.mono100
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(FutsalggColor.mono50)
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow1()
                            .shadow2()
                            .background(FutsalggColor.white)
                            .padding(horizontal = 16.dp)
                    ) {
                        VerticalSpacer16()
                        Column(
                            modifier = Modifier
                                .background(
                                    color = FutsalggColor.mono50,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(
                                    horizontal = 16.dp
                                )
                                .fillMaxWidth()
                        ) {
                            VerticalSpacer16()
                            Text(
                                text = teamState.introduction,
                                style = FutsalggTypography.regular_17_200,
                                color = FutsalggColor.mono900
                            )
                            VerticalSpacer8()
                            Text(
                                text = teamState.rule,
                                style = FutsalggTypography.light_17_200,
                                color = FutsalggColor.mono700
                            )
                            VerticalSpacer16()
                        }
                        VerticalSpacer16()
                    }
                    VerticalSpacer16()
                }

                val list = teamMembersState
                val lastIndex = list.size - 1
                itemsIndexed(list) { index, item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .clickable {
                                viewModel.setSelectedTeamMemberId(
                                    item.id
                                )
                                navController.navigate(RoutePath.PROFILE_CARD)
                            },
                    ) {
                        val modifier = if (list.size == 1) {
                            val roundedCornerShape = RoundedCornerShape(8.dp)
                            Modifier
                                .background(
                                    FutsalggColor.white,
                                    shape = roundedCornerShape
                                )
                                .border(
                                    width = 1.dp,
                                    color = FutsalggColor.mono200,
                                    shape = roundedCornerShape,
                                )
                        } else if (index == 0) {
                            val roundedCornerShape = RoundedCornerShape(
                                topStart = 8.dp,
                                topEnd = 8.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                            Modifier
                                .background(
                                    FutsalggColor.white,
                                    shape = roundedCornerShape
                                )
                                .border(
                                    width = 1.dp,
                                    color = FutsalggColor.mono200,
                                    shape = roundedCornerShape,
                                )
                        } else if (index == lastIndex) {
                            val roundedCornerShape = RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 0.dp,
                                bottomStart = 8.dp,
                                bottomEnd = 8.dp
                            )
                            Modifier
                                .background(
                                    FutsalggColor.white,
                                    shape = roundedCornerShape
                                )
                                .border(
                                    width = 1.dp,
                                    color = FutsalggColor.mono200,
                                    shape = roundedCornerShape,
                                )
                        } else if (index == lastIndex - 1) {
                            val roundedCornerShape = RoundedCornerShape(0.dp)
                            Modifier
                                .background(
                                    FutsalggColor.white,
                                    shape = roundedCornerShape
                                )
                                .sideBorder(
                                    color = FutsalggColor.mono200
                                )
                        } else {
                            val roundedCornerShape = RoundedCornerShape(0.dp)
                            Modifier
                                .background(
                                    FutsalggColor.white,
                                    shape = roundedCornerShape
                                )
                                .sideBorder(
                                    color = FutsalggColor.mono200
                                )
                                .bottomBorder(
                                    color = FutsalggColor.mono200
                                )
                        }
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(
                                    16.dp
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = item.profileUrl,
                                contentDescription = "프로필 이미지",
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape),
                                placeholder = painterResource(R.drawable.default_profile),
                                error = painterResource(R.drawable.default_profile)
                            )
                            Spacer(Modifier.width(16.dp))
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = item.name,
                                    style = FutsalggTypography.bold_17_200,
                                    color = FutsalggColor.mono900
                                )
                                VerticalSpacer4()
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    @Composable
                                    fun UserDetailText(text: String) {
                                        Text(
                                            text = text,
                                            style = FutsalggTypography.regular_17_200,
                                            color = FutsalggColor.mono700
                                        )
                                    }

                                    @Composable
                                    fun Ellipse() {
                                        Image(
                                            imageVector = ImageVector.vectorResource(R.drawable.ic_ellipse_2),
                                            contentDescription = null
                                        )
                                    }
                                    UserDetailText(item.role.displayName)
                                    Ellipse()
                                    UserDetailText(item.gender.displayName)
                                    Ellipse()
                                    UserDetailText(item.generation)
                                    Ellipse()
                                    UserDetailText(item.squadNumber.toString())
                                }
                            }
                            Image(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_forward_16),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}

fun Modifier.sideBorder(
    color: Color
) = this.drawWithContent {
    drawContent()
    drawLine(
        color = color,
        start = Offset(size.width - 0.5.dp.toPx(), 0.5.dp.toPx()),
        end = Offset(size.width - 0.5.dp.toPx(), size.height - 0.5.dp.toPx()),
        strokeWidth = 1.dp.toPx()
    )
    drawLine(
        color = color,
        start = Offset(0.5.dp.toPx(), 0.5.dp.toPx()),
        end = Offset(0.5.dp.toPx(), size.height - 0.5.dp.toPx()),
        strokeWidth = 1.dp.toPx()
    )
}

fun Modifier.bottomBorder(
    color: Color
) = this.drawWithContent {
    drawContent()
    drawLine(
        color = color,
        start = Offset(0.5.dp.toPx(), size.height - 0.5.dp.toPx()),
        end = Offset(size.width - 0.5.dp.toPx(), size.height - 0.5.dp.toPx()),
        strokeWidth = 1.dp.toPx()
    )
}