package com.futsalgg.app.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.futsalgg.app.R
import com.futsalgg.app.presentation.main.component.MainCardItem
import com.futsalgg.app.presentation.main.component.MainScreenTopBar
import com.futsalgg.app.ui.components.spacers.VerticalSpacer56
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val state = viewModel.mainState.collectAsState()

    Scaffold(
        modifier = Modifier
            .background(
                FutsalggColor.white
            )
            .windowInsetsPadding(WindowInsets.systemBars),
        topBar = {
            MainScreenTopBar(
                title = state.value.myTeam?.name ?: "팀이름",
                onRightIconClick = {
                    // TODO 설정
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .background(FutsalggColor.white)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            ) {
                VerticalSpacer56()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    FutsalggColor.mint500,
                                    FutsalggColor.blue400
                                )
                            )
                        )
                        .clickable {
                            // TODO On Click 경기 결과
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "yyyy-mm-dd에 진행한",
                                style = FutsalggTypography.regular_17_200,
                                color = FutsalggColor.white
                            )
                            Text(
                                text = "경기 결과를 확인해보세요.",
                                style = FutsalggTypography.bold_20_300,
                                color = FutsalggColor.white
                            )
                        }
                        Image(
                            painterResource(R.mipmap.football),
                            contentDescription = ""
                        )
                        Spacer(Modifier.width(6.dp))
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_forward_white_15),
                            contentDescription = ""
                        )
                    }
                }

                Spacer(Modifier.height(32.dp))
                Column {
                    Row {
                        MainCardItem(
                            text = "내 정보 보기",
                            image = ImageVector.vectorResource(R.drawable.img_main_profile),
                            color = FutsalggColor.blue50,
                            onClick = {
                                // TODO On Click 내정보
                            },
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(Modifier.width(16.dp))

                        MainCardItem(
                            text = "팀 정보 보기",
                            // TODO On Click 팀 정보
                            image = ImageVector.vectorResource(R.drawable.img_main_team),
                            color = FutsalggColor.mint50,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                Column {
                    Row {
                        MainCardItem(
                            text = "Coming Soon",
                            image = ImageVector.vectorResource(R.drawable.img_main_vote),
                            color = FutsalggColor.mono100,
                            color2 = FutsalggColor.mono100,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(Modifier.width(16.dp))

                        MainCardItem(
                            text = "Coming Soon",
                            image = ImageVector.vectorResource(R.drawable.img_main_due),
                            color = FutsalggColor.mono100,
                            color2 = FutsalggColor.mono100,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // TODO 관리자 after api update.
            if (state.value.myTeam?.isManager != false) {
                Box(
                    modifier = Modifier
                        .padding(end = 16.dp, bottom = 32.dp)
                        .height(56.dp)
                        .clip(RoundedCornerShape(28.dp))
                        .background(FutsalggColor.mono800)
                        .align(Alignment.BottomEnd)
                        .clickable {
                            // TODO 관리자 메뉴
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(
                            horizontal = 16.dp
                        ).align(Alignment.Center),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_add_white_16),
                            contentDescription = ""
                        )
                        Spacer(Modifier.width(8.dp))

                        Text(
                            text = "관리자 메뉴",
                            style = FutsalggTypography.bold_20_300,
                            color = FutsalggColor.white
                        )
                    }
                }
            }
        }
    }
}