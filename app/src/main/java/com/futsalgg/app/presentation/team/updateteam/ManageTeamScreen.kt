package com.futsalgg.app.presentation.team.updateteam

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.futsalgg.app.presentation.common.model.TeamRole
import com.futsalgg.app.presentation.common.screen.BaseScreen
import com.futsalgg.app.presentation.team.teaminfo.TeamMemberState
import com.futsalgg.app.ui.components.UserInfoRow
import com.futsalgg.app.ui.components.spacers.VerticalSpacer12
import com.futsalgg.app.ui.components.spacers.VerticalSpacer16
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography
import com.futsalgg.app.util.shadow1
import com.futsalgg.app.util.shadow2

@Composable
fun UpdateTeamScreen(
    navController: NavController,
    viewModel: ManageTeamViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    BaseScreen(
        navController = navController,
        screenName = RoutePath.MANAGE_TEAM,
        title = stringResource(R.string.update_team_title_text),
        uiState = uiState,
        rightIcon = ImageVector.vectorResource(R.drawable.ic_meatball_menu),
        onRightClick = {
            // TODO On Right Click
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(FutsalggColor.white)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow1()
                    .shadow2()
                    .background(FutsalggColor.white),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                VerticalSpacer16()
                // TODO Team logo
                AsyncImage(
                    model = "teamState.logoUrl",
                    contentDescription = "프로필 이미지",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape),
                    placeholder = painterResource(R.drawable.ic_team_default_56),
                    error = painterResource(R.drawable.ic_team_default_56)
                )
                VerticalSpacer16()
                // TODO Team Name
                Text(
                    text = "팀명",
                    style = FutsalggTypography.bold_17_200,
                    color = FutsalggColor.mono900
                )
                VerticalSpacer16()
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val shape = RoundedCornerShape(24.dp)
                Box(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .padding(vertical = 16.dp)
                        .background(
                            color = FutsalggColor.mono900,
                            shape = shape
                        )
                        .clip(shape)
                        .clickable {
                            // TODO 전체 클릭
                        }
                ) {
                    Text(
                        modifier = Modifier.padding(
                            vertical = 8.dp,
                            horizontal = 16.dp
                        ),
                        text = stringResource(R.string.update_team_all_text),
                        style = FutsalggTypography.bold_17_200,
                        color = FutsalggColor.white
                    )
                }
                Spacer(Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .background(
                            color = FutsalggColor.white,
                            shape = shape
                        )
                        .border(
                            width = 1.dp,
                            color = FutsalggColor.mono200,
                            shape = shape
                        )
                        .clip(shape)
                        .clickable {
                            // TODO 신청 클릭
                        }
                ) {
                    Text(
                        modifier = Modifier.padding(
                            vertical = 8.dp,
                            horizontal = 16.dp
                        ),
                        text = stringResource(R.string.apply_text),
                        style = FutsalggTypography.bold_17_200,
                        color = FutsalggColor.mono900
                    )
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(FutsalggColor.mono50)
            ) {
                item {
                    VerticalSpacer12()
                }
                items(
                    listOf(
                        "닉네임닉네임", "닉네임닉네임닉네임닉","메메메메", "닉네임닉닉네임닉네","닉네임", "닉네임닉네","닉네임닉네임닉닉", "닉네임닉네임","닉네임닉네임", "닉네임닉네임","닉네임닉네임", "닉네임닉네임","닉네임닉네임", "닉네임닉네임",
                    )
                ) {
                    // TODO API
                    UserInfoRow(
                        modifier = Modifier
                            .background(FutsalggColor.white),
                        profileUrl = "",
                        name = it,
                        role = TeamRole.TEAM_MEMBER,
                        status = TeamMemberState.TeamMemberStatus.PENDING,
//                        endIcon = null,
                        onClick = {
                            // TODO 온클릭
                        },
                    )
                }
            }
        }
    }
}