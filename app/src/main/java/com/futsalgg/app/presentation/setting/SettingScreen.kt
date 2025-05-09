package com.futsalgg.app.presentation.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.futsalgg.app.R
import com.futsalgg.app.navigation.RoutePath
import com.futsalgg.app.presentation.common.screen.BaseScreen
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.ui.components.spacers.VerticalSpacer16
import com.futsalgg.app.ui.components.spacers.VerticalSpacer24
import com.futsalgg.app.ui.components.spacers.VerticalSpacer32
import com.futsalgg.app.ui.components.spacers.VerticalSpacer56
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun SettingScreen(
    navController: NavController,
    viewModel: SettingViewModel = hiltViewModel()
) {

    var columnWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    val scrollState = rememberScrollState()

    val uiState by viewModel.uiState.collectAsState(UiState.Initial)
    val settingState by viewModel.settingState.collectAsState()

    BaseScreen(
        navController = navController,
        screenName = RoutePath.SETTING,
        title = stringResource(R.string.setting_text),
        uiState = uiState
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(FutsalggColor.mono50)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(
                        scrollState
                    )
            ) {

                VerticalSpacer16()

                Column(
                    modifier = Modifier
                        .background(FutsalggColor.white)
                        .clip(RoundedCornerShape(8.dp))
                        .border(
                            width = 1.dp,
                            color = FutsalggColor.mono200,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    VerticalSpacer32()

                    AsyncImage(
                        model = settingState.profileUrl,
                        contentDescription = "프로필 이미지",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(shape = CircleShape),
                        placeholder = painterResource(R.drawable.default_profile),
                        error = painterResource(R.drawable.default_profile)
                    )
                    VerticalSpacer16()

                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .onSizeChanged { size ->
                                    columnWidth = with(density) { size.width.toDp() }
                                },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = settingState.name,
                                style = FutsalggTypography.bold_20_300,
                                color = FutsalggColor.mono900
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = settingState.email,
                                style = FutsalggTypography.regular_17_200,
                                color = FutsalggColor.mono700
                            )
                        }
                        IconButton(
                            onClick = {
                                navController.navigate(RoutePath.UPDATE_PROFILE)
                            },
                            modifier = Modifier
                                .align(Alignment.Center)
                                .offset{ IntOffset(
                                    x = (columnWidth / 2 + 32.dp).roundToPx(),
                                    y = 0
                                ) }
                        ) {
                            Image(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_edit_24),
                                contentDescription = ""
                            )
                        }
                    }

                    VerticalSpacer24()
                }

                VerticalSpacer16()

                Column(
                    modifier = Modifier
                        .background(FutsalggColor.white)
                        .clip(RoundedCornerShape(8.dp))
                        .border(
                            width = 1.dp,
                            color = FutsalggColor.mono200,
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    // TODO Notification 구현되면 키기
//                    TextWithImage(
//                        text = "알림",
//                        image = ImageVector.vectorResource(R.drawable.ic_toggle_true),
//                        onClick = {
//                            // TODO 알림
//                        }
//                    )
//                    HorizontalDivider(
//                        thickness = 1.dp,
//                        color = FutsalggColor.mono100
//                    )
                    TextWithImage(
                        text = stringResource(R.string.announcement_text),
                        image = ImageVector.vectorResource(R.drawable.ic_arrow_forward_14),
                        onClick = {
                            // TODO 공지사항
                        }
                    )
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = FutsalggColor.mono100
                    )
                    TextWithImage(
                        text = stringResource(R.string.terms_and_condition_text),
                        image = ImageVector.vectorResource(R.drawable.ic_arrow_forward_14),
                        onClick = {
                            // TODO 이용 약관
                        }
                    )
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = FutsalggColor.mono100
                    )
                    TextWithImage(
                        text = stringResource(R.string.personal_information_text),
                        image = ImageVector.vectorResource(R.drawable.ic_arrow_forward_14),
                        onClick = {
                            // TODO 개인정보 처리방침
                        }
                    )
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = FutsalggColor.mono100
                    )
                    TextWithImage(
                        text = stringResource(R.string.logout_text),
                        image = ImageVector.vectorResource(R.drawable.ic_arrow_forward_14),
                        onClick = {
                            // TODO 로그아웃
                        }
                    )
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = FutsalggColor.mono100
                    )
                    TextWithImage(
                        text = stringResource(R.string.delete_user_text),
                        image = ImageVector.vectorResource(R.drawable.ic_arrow_forward_14),
                        onClick = {
                            // TODO 회원 탈퇴
                        }
                    )
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "궁금한 점이 있으신가요? futsal@gmail.com으로 \n" +
                            "문의나 개선 사항을 보내주세요.",
                    style = FutsalggTypography.regular_15_100,
                    color = FutsalggColor.mono700
                )

                VerticalSpacer56()
            }
        }
    }
}

@Composable
fun TextWithImage(
    text: String,
    image: ImageVector,
    onClick: () -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Text(
            text = text,
            style = FutsalggTypography.bold_17_200,
            color = FutsalggColor.mono900,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(vertical = 16.dp)
                .padding(start = 16.dp)
        )
        Image(
            imageVector = image,
            contentDescription = "",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
        )
    }
}