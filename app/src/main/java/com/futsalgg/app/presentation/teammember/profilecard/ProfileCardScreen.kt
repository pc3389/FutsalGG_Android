package com.futsalgg.app.presentation.teammember.profilecard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.futsalgg.app.R
import com.futsalgg.app.navigation.RoutePath
import com.futsalgg.app.presentation.common.screen.BaseScreen
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography
import com.futsalgg.app.util.toDateFormat

@Composable
fun ProfileCardScreen(
    navController: NavController,
    viewModel: ProfileCardViewModel = hiltViewModel()
) {
    // TODO API 보류..!

    val uiState = viewModel.uiState.collectAsState().value
    val state = viewModel.profileState.collectAsState()

    BaseScreen(
        navController = navController,
        screenName = RoutePath.PROFILE_CARD,
        title = stringResource(R.string.my_profile_title),
        uiState = uiState
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(FutsalggColor.mono50)
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                // 카드
                Box(
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                        .height(468.dp)
                        .fillMaxWidth()
                        .align(Alignment.Center)
                ) {
                    // 카드 배경
                    Image(
                        painter = painterResource(R.mipmap.profile_bg),
                        contentDescription = "",
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillBounds
                    )

                    // 날짜
                    Text(
                        modifier = Modifier
                            .padding(top = 12.dp, end = 16.dp)
                            .align(Alignment.TopEnd),
                        text = state.value.createdTime?.toDateFormat(stringResource(R.string.date_format_dot_ymd)) ?: "",
                        style = FutsalggTypography.regular_15_100,
                        color = FutsalggColor.white
                    )
                    Column {
                        Row {
                            // 검은 구간 (등번호, 로고)
                            Box(
                                modifier = Modifier
                                    .padding(start = 24.dp)
                                    .padding(top = 4.dp)
                                    .width(72.dp)
                                    .height(300.dp)
                                    .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(
                                                FutsalggColor.mono900,
                                                Color.Transparent
                                            )
                                        )
                                    )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.TopCenter)
                                        .padding(top = 56.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = stringResource(R.string.my_profile_back_number_label),
                                        style = FutsalggTypography.regular_15_100,
                                        color = FutsalggColor.white,
                                    )
                                    Spacer(Modifier.height(10.dp))
                                    Text(
                                        text = state.value.squadNumber ?: "00",
                                        style = FutsalggTypography.bold_40_500,
                                        color = FutsalggColor.white
                                    )
                                    Spacer(Modifier.height(18.dp))
                                    Image(
                                        imageVector = ImageVector.vectorResource(R.drawable.img_profile_back_number_divider),
                                        contentDescription = ""
                                    )
                                    Spacer(Modifier.height(32.dp))
                                    AsyncImage(
                                        model = state.value.teamLogoUrl,
                                        contentDescription = "프로필 이미지",
                                        modifier = Modifier
                                            .size(56.dp)
                                            .clip(CircleShape),
                                        placeholder = painterResource(R.drawable.ic_team_default_56),
                                        error = painterResource(R.drawable.ic_team_default_56)
                                    )
                                }
                            }

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .align(Alignment.CenterVertically),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Profile Picture
                                AsyncImage(
                                    model = state.value.profileUrl,
                                    contentDescription = "프로필 이미지",
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(CircleShape),
                                    placeholder = painterResource(R.drawable.default_profile),
                                    error = painterResource(R.drawable.default_profile)
                                )

                                Spacer(Modifier.height(16.dp))

                                // 닉네임
                                Text(
                                    text = state.value.name,
                                    style = FutsalggTypography.bold_17_200,
                                    color = FutsalggColor.white
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = state.value.teamName,
                                    style = FutsalggTypography.regular_15_100,
                                    color = FutsalggColor.white
                                )
                            }
                        }

                        Spacer(Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp)
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "나이대",
                                    style = FutsalggTypography.regular_15_100,
                                    color = FutsalggColor.white
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = state.value.generation,
                                    style = FutsalggTypography.bold_17_200,
                                    color = FutsalggColor.white
                                )
                            }

                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "직책",
                                    style = FutsalggTypography.regular_15_100,
                                    color = FutsalggColor.white
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = state.value.role.displayName,
                                    style = FutsalggTypography.bold_17_200,
                                    color = FutsalggColor.white
                                )
                            }
                        }

                        Spacer(Modifier.height(8.dp))

                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            thickness = 1.dp,
                            color = FutsalggColor.blue500.copy(alpha = 0.3f)
                        )

                        Spacer(Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp)
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "참여 경기",
                                    style = FutsalggTypography.regular_15_100,
                                    color = FutsalggColor.white
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = "${state.value.history.size} / ${state.value.totalGameNum}",
                                    style = FutsalggTypography.bold_17_200,
                                    color = FutsalggColor.white
                                )
                            }

                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row {
                                    Text(
                                        text = "승률",
                                        style = FutsalggTypography.regular_15_100,
                                        color = FutsalggColor.white
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Text(
                                        text = "${viewModel.getWinRate(state.value.history)}%",
                                        style = FutsalggTypography.bold_17_200,
                                        color = FutsalggColor.white
                                    )
                                }
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = viewModel.getStatString(state.value.history),
                                    style = FutsalggTypography.light_15_100,
                                    color = FutsalggColor.white
                                )
                            }
                        }
                    }
                }
            }

            Image(
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth(),
                painter = painterResource(R.mipmap.profile_card_shadow),
                contentScale = ContentScale.FillBounds,
                contentDescription = ""
            )
        }
    }
}