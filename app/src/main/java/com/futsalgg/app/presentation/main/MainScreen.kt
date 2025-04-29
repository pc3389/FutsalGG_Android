package com.futsalgg.app.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.futsalgg.app.R
import com.futsalgg.app.navigation.RoutePath
import com.futsalgg.app.presentation.main.component.MainCardItem
import com.futsalgg.app.presentation.main.component.MainScreenTopBar
import com.futsalgg.app.ui.components.spacers.VerticalSpacer16
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val state by viewModel.mainState.collectAsState()
    val scrollState = rememberScrollState()
    var showAdminMenu by remember { mutableStateOf(false) }

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val heightPadding = remember(screenHeight) {
        if (screenHeight >= 700.dp) 90.dp else 0.dp
    }

    Scaffold(
        modifier = Modifier
            .background(
                FutsalggColor.white
            )
            .windowInsetsPadding(WindowInsets.systemBars),
        topBar = {
            MainScreenTopBar(
                state.myTeam.name,
                onRightIconClick = {
                    navController.navigate(RoutePath.SETTING)
                },
                teamRole = state.myTeam.role,
                access = state.myTeam.access
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = heightPadding)
                .background(FutsalggColor.white)
                .fillMaxSize()
        ) {
            Box {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .verticalScroll(scrollState)
                ) {
                    Spacer(Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        FutsalggColor.mint500,
                                        FutsalggColor.blue400
                                    )
                                )
                            )
                            .clickable {
                                navController.navigate(RoutePath.MATCH_RESULT)
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(
                                horizontal = 16.dp,
                                vertical = 10.dp
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = stringResource(
                                        R.string.main_recent_match,
                                        state.recentMatchDate
                                    ),
                                    style = FutsalggTypography.regular_17_200,
                                    color = FutsalggColor.white
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = stringResource(R.string.main_check_match_result_text),
                                    style = FutsalggTypography.bold_20_300,
                                    color = FutsalggColor.white
                                )
                            }
                            Image(
                                modifier = Modifier.size(72.dp),
                                painter = painterResource(R.mipmap.football),
                                contentDescription = ""
                            )
                            Spacer(Modifier.width(6.dp))
                            Image(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_forward_white_15),
                                contentDescription = ""
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))
                    Column {
                        Row {
                            MainCardItem(
                                text = stringResource(R.string.main_show_my_profile),
                                image = ImageVector.vectorResource(R.drawable.img_main_profile),
                                color = FutsalggColor.blue50,
                                onClick = {
                                    navController.navigate(RoutePath.PROFILE_CARD)
                                },
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(Modifier.width(16.dp))

                            MainCardItem(
                                text = stringResource(R.string.main_show_team_profile),
                                image = ImageVector.vectorResource(R.drawable.img_main_team),
                                color = FutsalggColor.mint50,
                                onClick = {
                                    navController.navigate(RoutePath.TEAM_INFO)
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))
                }
            }
            if (state.myTeam.isManager) {
                if (!showAdminMenu) {
                    FloatingActionButton(
                        onClick = {
                            showAdminMenu = true
                        },
                        modifier = Modifier
                            .padding(end = 16.dp, bottom = 32.dp)
                            .align(Alignment.BottomEnd)
                            .height(56.dp),
                        containerColor = FutsalggColor.mono800,
                        shape = RoundedCornerShape(28.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
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
            if (showAdminMenu) {
                AdminMenuPopup(
                    onDismiss = { showAdminMenu = false },
                    onClickCreateMatch = {
                        navController.navigate(RoutePath.CREATE_MATCH)
                    },
                    onClickUpdateMatchResult = {
                        navController.navigate(RoutePath.MATCH_RESULT)
                    },
                    onClickManageTeam = {
                        navController.navigate(RoutePath.MANAGE_TEAM)
                    }
                )
            }
        }
    }
}

@Composable
fun AdminMenuPopup(
    onDismiss: () -> Unit,
    onClickCreateMatch: () -> Unit,
    onClickUpdateMatchResult: () -> Unit,
    onClickManageTeam: () -> Unit
) {
    // 배경을 어둡게 만드는 Box
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FutsalggColor.mono900.copy(alpha = 0.5f))
            .clickable { onDismiss() }
    ) {
        Column(
            Modifier
                .background(Color.Transparent)
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .align(Alignment.BottomCenter)
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        width = 1.dp,
                        color = FutsalggColor.mono100,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(FutsalggColor.white)
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .background(FutsalggColor.white)
                        .fillMaxWidth()
                        .clickable {
                            onClickCreateMatch()
                        }
                ) {
                    Text(
                        text = stringResource(R.string.create_match_title),
                        style = FutsalggTypography.bold_17_200,
                        color = FutsalggColor.mono900,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(vertical = 12.dp)
                    )
                }
                HorizontalDivider(thickness = 1.dp, color = FutsalggColor.mono100)
                Box(
                    modifier = Modifier
                        .background(FutsalggColor.white)
                        .fillMaxWidth()
                        .clickable {
                            onClickUpdateMatchResult()
                        }
                ) {
                    Text(
                        text = stringResource(R.string.create_or_update_match_result),
                        style = FutsalggTypography.bold_17_200,
                        color = FutsalggColor.mono900,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(vertical = 12.dp)
                    )
                }
                HorizontalDivider(thickness = 1.dp, color = FutsalggColor.mono100)
                Box(
                    modifier = Modifier
                        .background(FutsalggColor.white)
                        .fillMaxWidth()
                        .clickable {
                            onClickManageTeam()
                        }
                ) {
                    Text(
                        text = stringResource(R.string.main_manage_team),
                        style = FutsalggTypography.bold_17_200,
                        color = FutsalggColor.mono900,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(vertical = 12.dp)
                    )
                }
            }
            VerticalSpacer16()

            Box(
                Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(FutsalggColor.mint50)
                    .align(Alignment.End)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_close_24),
                    contentDescription = "",
                    tint = FutsalggColor.mono800,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            VerticalSpacer16()
        }
    }
}