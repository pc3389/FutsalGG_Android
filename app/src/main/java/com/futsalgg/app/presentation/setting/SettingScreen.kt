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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.futsalgg.app.R
import com.futsalgg.app.presentation.common.screen.BaseScreen
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.ui.components.spacers.VerticalSpacer16
import com.futsalgg.app.ui.components.spacers.VerticalSpacer24
import com.futsalgg.app.ui.components.spacers.VerticalSpacer32
import com.futsalgg.app.ui.components.spacers.VerticalSpacer56
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography
import org.w3c.dom.Text

@Composable
fun SettingScreen(
    navController: NavController,
    viewModel: SettingViewModel = hiltViewModel()
) {

    var columnWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    val scrollState = rememberScrollState()

    val uiState = viewModel.uiState.collectAsState(UiState.Initial)

    BaseScreen(
        navController = navController,
        title = stringResource(R.string.setting_text),
        uiState = uiState.value
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
                    Image(
                        modifier = Modifier.size(80.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.default_profile),
                        contentDescription = ""
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
                                // TODO 닉네임 from viewmodel
                                text = "닉네임을입력해주세요",
                                style = FutsalggTypography.bold_20_300,
                                color = FutsalggColor.mono900
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "futsal@gmail.com",
                                style = FutsalggTypography.regular_17_200,
                                color = FutsalggColor.mono700
                            )
                        }
                        Image(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .offset(x = (columnWidth / 2 + 32.dp)),
                            imageVector = ImageVector.vectorResource(R.drawable.ic_edit_24),
                            contentDescription = ""
                        )
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
                    TextWithImage(
                        text = "알림",
                        image = ImageVector.vectorResource(R.drawable.ic_toggle_true),
                        onClick = {
                            // TODO 알림
                        }
                    )
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = FutsalggColor.mono100
                    )
                    TextWithImage(
                        text = "공지사항",
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
                        text = "이용 약관",
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
                        text = "개인정보 처리방침",
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
                        text = "로그아웃",
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
                        text = "회원 탈퇴",
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