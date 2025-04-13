package com.futsalgg.app.presentation.user.profile

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.futsalgg.app.R
import com.futsalgg.app.presentation.common.screen.BaseScreen
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun MyProfileScreen(
    navController: NavController,
    viewModel: MyProfileViewModel = hiltViewModel()
) {
    // TODO API 보류..!

    val uiState = viewModel.uiState.collectAsState().value
    val state = viewModel.profileState.collectAsState()

    BaseScreen(
        navController = navController,
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
                    // TODO 날짜 업데이트
                    Text(
                        modifier = Modifier
                            .padding(top = 12.dp, end = 16.dp)
                            .align(Alignment.TopEnd),
                        text = "yyyy.MM.dd",
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
                                        // TODO Back Number
                                        text = "00",
                                        style = FutsalggTypography.bold_40_500,
                                        color = FutsalggColor.white
                                    )
                                    Spacer(Modifier.height(18.dp))
                                    Image(
                                        imageVector = ImageVector.vectorResource(R.drawable.img_profile_back_number_divider),
                                        contentDescription = ""
                                    )
                                    Spacer(Modifier.height(32.dp))
                                    Image(
                                        modifier = Modifier.size(56.dp),
                                        imageVector = ImageVector.vectorResource(R.drawable.ic_team_default_56),
                                        contentDescription = ""
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
                                Image(
                                    painter = painterResource(R.drawable.default_profile),
                                    contentDescription = ""
                                )

                                Spacer(Modifier.height(16.dp))

                                // 닉네임
                                // TODO 닉네임
                                Text(
                                    text = "닉네임을 입력해주세요",
                                    style = FutsalggTypography.bold_17_200,
                                    color = FutsalggColor.white
                                )
                                Spacer(Modifier.height(4.dp))
                                // TODO 팀 명
                                Text(
                                    text = "팀 명을 입력해주세요",
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
                                    text = "00",
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
                                    text = "직책이름",
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
                                    text = "nnn / nnn",
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
                                        text = "nnn%",
                                        style = FutsalggTypography.bold_17_200,
                                        color = FutsalggColor.white
                                    )
                                }
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = "nn승 / nn무 / nn패",
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