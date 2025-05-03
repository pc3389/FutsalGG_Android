package com.futsalgg.app.presentation.team.manageteam

import androidx.compose.foundation.Image
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.futsalgg.app.R
import com.futsalgg.app.navigation.RoutePath
import com.futsalgg.app.presentation.common.model.TeamRole
import com.futsalgg.app.presentation.common.screen.BaseScreen
import com.futsalgg.app.presentation.team.teaminfo.TeamMemberState
import com.futsalgg.app.ui.components.BottomButton
import com.futsalgg.app.ui.components.UserInfoRow
import com.futsalgg.app.ui.components.spacers.VerticalSpacer12
import com.futsalgg.app.ui.components.spacers.VerticalSpacer16
import com.futsalgg.app.ui.components.spacers.VerticalSpacer32
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography
import com.futsalgg.app.util.shadow1
import com.futsalgg.app.util.shadow2
import com.futsalgg.app.util.toPx

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageTeamScreen(
    navController: NavController,
    viewModel: ManageTeamViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val teamState by viewModel.teamState.collectAsState()
    val teamMemberState by viewModel.teamMemberState.collectAsState()

    var showPending by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }
    var updateRole by remember { mutableStateOf(false) }
    var updateStatus by remember { mutableStateOf(false) }
    var showPendingBottomSheet by remember { mutableStateOf(false) }
    var showUpdateStateBottomSheet by remember { mutableStateOf(false) }
    var showUpdateRoleBottomSheet by remember { mutableStateOf(false) }

    BaseScreen(
        navController = navController,
        screenName = RoutePath.MANAGE_TEAM,
        title = stringResource(R.string.manage_team_title_text),
        uiState = uiState,
        rightIcon = ImageVector.vectorResource(R.drawable.ic_meatball_menu),
        onRightClick = {
            showMenu = !showMenu
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .background(FutsalggColor.white)
        ) {
            Column {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow1()
                        .shadow2()
                        .background(FutsalggColor.white),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    VerticalSpacer16()

                    // Team logo
                    AsyncImage(
                        model = teamState?.logoUrl ?: "",
                        contentDescription = "프로필 이미지",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape),
                        placeholder = painterResource(R.drawable.ic_team_default_56),
                        error = painterResource(R.drawable.ic_team_default_56)
                    )
                    VerticalSpacer16()

                    // Team Name
                    Text(
                        text = teamState?.name ?: "",
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
                            .border(
                                width = 1.dp,
                                color = if (!showPending) FutsalggColor.white else FutsalggColor.mono200,
                                shape = shape
                            )
                            .background(
                                color = if (!showPending) FutsalggColor.mono900 else FutsalggColor.white,
                                shape = shape
                            )
                            .clip(shape)
                            .clickable {
                                showPending = false
                            }
                    ) {
                        Text(
                            modifier = Modifier.padding(
                                vertical = 8.dp,
                                horizontal = 16.dp
                            ),
                            text = stringResource(R.string.manage_team_all_text),
                            style = FutsalggTypography.bold_17_200,
                            color = if (!showPending) FutsalggColor.white else FutsalggColor.mono900
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .background(
                                color = if (!showPending) FutsalggColor.white else FutsalggColor.mono900,
                                shape = shape
                            )
                            .border(
                                width = 1.dp,
                                color = if (!showPending) FutsalggColor.mono200 else FutsalggColor.white,
                                shape = shape
                            )
                            .clip(shape)
                            .clickable {
                                showPending = true
                            }
                    ) {
                        Text(
                            modifier = Modifier.padding(
                                vertical = 8.dp,
                                horizontal = 16.dp
                            ),
                            text = stringResource(R.string.apply_text),
                            style = FutsalggTypography.bold_17_200,
                            color = if (!showPending) FutsalggColor.mono900 else FutsalggColor.white
                        )
                    }
                }
                if (showPending && teamMemberState.none {
                        it.status == TeamMemberState.TeamMemberStatus.PENDING
                    }) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(FutsalggColor.mono50),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.img_empty_128),
                            contentDescription = null
                        )
                        VerticalSpacer16()
                        Text(
                            text = stringResource(R.string.manage_team_no_pending_text),
                            style = FutsalggTypography.bold_17_200,
                            color = FutsalggColor.mono500
                        )
                        Spacer(modifier = Modifier.weight(2f))
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(FutsalggColor.mono50)
                    ) {
                        item {
                            VerticalSpacer12()
                        }
                        items(
                            teamMemberState.filter {
                                if (showPending) {
                                    it.status == TeamMemberState.TeamMemberStatus.PENDING
                                } else {
                                    it.status != TeamMemberState.TeamMemberStatus.PENDING
                                }
                            }
                        ) {
                            UserInfoRow(
                                modifier = Modifier
                                    .background(FutsalggColor.white),
                                profileUrl = it.profileUrl,
                                name = it.name,
                                role = it.role,
                                status = it.status,
                                endIcon = if (it.status == TeamMemberState.TeamMemberStatus.PENDING || updateRole || updateStatus) {
                                    ImageVector.vectorResource(R.drawable.ic_arrow_forward_16)
                                } else {
                                    null
                                },
                                onClick = {
                                    if (it.status == TeamMemberState.TeamMemberStatus.PENDING) {
                                        showPendingBottomSheet = true
                                    } else if (updateRole) {
                                        showUpdateRoleBottomSheet = true
                                    } else {
                                        showUpdateStateBottomSheet = true
                                    }
                                },
                            )
                        }
                    }
                }
            }
            if (showMenu) {
                Popup(
                    onDismissRequest = { showMenu = false },
                    offset = IntOffset(
                        x = -16.dp.toPx(LocalContext.current).toInt(),
                        y = 0
                    ),
                    alignment = Alignment.TopEnd,
                ) {
                    Column(
                        modifier = Modifier
                            .shadow1()
                            .shadow2()
                            .background(
                                color = FutsalggColor.white,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                width = 1.dp,
                                shape = RoundedCornerShape(8.dp),
                                color = FutsalggColor.mono200
                            )
                            .align(Alignment.TopEnd)
                            .width(160.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(RoutePath.UPDATE_TEAM)
                                    showMenu = false
                                }
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(
                                        vertical = 12.dp,
                                        horizontal = 16.dp
                                    ),
                                text = stringResource(R.string.manage_team_update_team_text),
                                style = FutsalggTypography.regular_17_200,
                                color = FutsalggColor.mono900
                            )
                        }
                        if ((teamState?.role?.rank ?: 0) <= TeamRole.TEAM_LEADER.rank) {
                            HorizontalDivider(
                                thickness = 1.dp,
                                color = FutsalggColor.mono200
                            )
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        updateRole = !updateRole
                                        updateStatus = false
                                        showMenu = false
                                    }
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(
                                            vertical = 12.dp,
                                            horizontal = 16.dp
                                        )
                                        .align(Alignment.Center),
                                    text = stringResource(R.string.manage_team_update_role),
                                    style = FutsalggTypography.regular_17_200,
                                    color = FutsalggColor.mono900
                                )
                            }
                            HorizontalDivider(
                                thickness = 1.dp,
                                color = FutsalggColor.mono200
                            )
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        updateStatus = !updateStatus
                                        updateRole = false
                                        showMenu = false
                                    }
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(
                                            vertical = 12.dp,
                                            horizontal = 16.dp
                                        )
                                        .align(Alignment.Center),
                                    text = stringResource(R.string.manage_team_update_status),
                                    style = FutsalggTypography.regular_17_200,
                                    color = FutsalggColor.mono900
                                )
                            }
                        }
                    }
                }
            }
        }
        if (showPendingBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showPendingBottomSheet = false },
                sheetState = rememberModalBottomSheetState(
                    skipPartiallyExpanded = true
                ),
                containerColor = FutsalggColor.white,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            ) {
                var selectedIndex by remember { mutableIntStateOf(-1) }
                Column {
                    Text(
                        text = stringResource(R.string.manage_team_state_bottom_sheet_title),
                        style = FutsalggTypography.bold_20_300,
                        color = FutsalggColor.mono900
                    )
                    VerticalSpacer16()
                    BottomSheetItemBox(
                        text = stringResource(R.string.manage_team_pending_bottom_sheet_add),
                        isSelected = selectedIndex == 1,
                        onClick = {
                            selectedIndex = if (selectedIndex == 1) -1 else 1
                        }
                    )
                    VerticalSpacer16()
                    BottomSheetItemBox(
                        text = stringResource(R.string.manage_team_pending_bottom_sheet_remove),
                        isSelected = selectedIndex == 2,
                        onClick = {
                            selectedIndex = if (selectedIndex == 2) -1 else 2
                        }
                    )
                    VerticalSpacer32()
                }
                BottomButton(
                    text = stringResource(R.string.select_long),
                    onClick = {
                        if (selectedIndex == 1) {
                            // TODO 신청 수락 버튼
                        } else {
                            // TODO 신청 거절 버튼
                        }
                    },
                    enabled = selectedIndex != -1,
                )
            }
        }

        if (showUpdateRoleBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showUpdateRoleBottomSheet = false },
                sheetState = rememberModalBottomSheetState(
                    skipPartiallyExpanded = true
                ),
                containerColor = FutsalggColor.white,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            ) {
                var selectedIndex by remember { mutableIntStateOf(-1) }
                Column {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        text = stringResource(R.string.manage_team_state_bottom_sheet_title),
                        style = FutsalggTypography.bold_20_300,
                        color = FutsalggColor.mono900
                    )
                    VerticalSpacer16()
                    Row(
                        Modifier.fillMaxWidth()
                    ) {
                        Spacer(Modifier.width(16.dp))
                        BottomSheetItemBox(
                            modifier = Modifier
                                .weight(1f),
                            text = TeamRole.TEAM_LEADER.displayName,
                            isSelected = selectedIndex == 1,
                            onClick = {
                                selectedIndex = if (selectedIndex == 1) -1 else 1
                            }
                        )
                        Spacer(Modifier.width(16.dp))
                        BottomSheetItemBox(
                            modifier = Modifier
                                .weight(1f),
                            text = TeamRole.TEAM_DEPUTY_LEADER.displayName,
                            isSelected = selectedIndex == 2,
                            onClick = {
                                selectedIndex = if (selectedIndex == 2) -1 else 2
                            }
                        )
                        Spacer(Modifier.width(16.dp))
                    }
                    VerticalSpacer16()
                    Row(
                        Modifier.fillMaxWidth()
                    ) {
                        Spacer(Modifier.width(16.dp))
                        BottomSheetItemBox(
                            modifier = Modifier
                                .weight(1f),
                            text = TeamRole.TEAM_SECRETARY.displayName,
                            isSelected = selectedIndex == 3,
                            onClick = {
                                selectedIndex = if (selectedIndex == 3) -1 else 3
                            }
                        )
                        Spacer(Modifier.width(16.dp))
                        BottomSheetItemBox(
                            modifier = Modifier
                                .weight(1f),
                            text = TeamRole.TEAM_MEMBER.displayName,
                            isSelected = selectedIndex == 4,
                            onClick = {
                                selectedIndex = if (selectedIndex == 4) -1 else 4
                            }
                        )
                        Spacer(Modifier.width(16.dp))
                    }
                    VerticalSpacer32()
                }
                BottomButton(
                    text = stringResource(R.string.select_long),
                    onClick = {
                        //TODO 롤 변경 버튼 클릭
                    },
                    enabled = selectedIndex != -1,
                )
            }
        }

        if (showUpdateStateBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showUpdateStateBottomSheet = false },
                sheetState = rememberModalBottomSheetState(
                    skipPartiallyExpanded = true
                ),
                containerColor = FutsalggColor.white,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            ) {
                var selectedIndex by remember { mutableIntStateOf(-1) }
                Column {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = stringResource(R.string.manage_team_state_bottom_sheet_title),
                        style = FutsalggTypography.bold_20_300,
                        color = FutsalggColor.mono900
                    )
                    VerticalSpacer16()
                    BottomSheetItemBox(
                        text = stringResource(R.string.manage_team_state_bottom_sheet_add),
                        isSelected = selectedIndex == 1,
                        onClick = {
                            selectedIndex = if (selectedIndex == 1) -1 else 1
                        }
                    )
                    VerticalSpacer16()
                    BottomSheetItemBox(
                        text = stringResource(R.string.manage_team_state_bottom_sheet_rest),
                        isSelected = selectedIndex == 2,
                        onClick = {
                            selectedIndex = if (selectedIndex == 2) -1 else 2
                        }
                    )
                    VerticalSpacer16()
                    BottomSheetItemBox(
                        text = stringResource(R.string.manage_team_state_bottom_sheet_remove),
                        isSelected = selectedIndex == 3,
                        onClick = {
                            selectedIndex = if (selectedIndex == 3) -1 else 3
                        }
                    )
                    VerticalSpacer32()
                }
                BottomButton(
                    text = stringResource(R.string.select_long),
                    onClick = {
                        //TODO 스테이트 변경 버튼 클릭
                    },
                    enabled = selectedIndex != -1,
                )
            }
        }
    }
}

@Composable
fun BottomSheetItemBox(
    text: String,
    modifier: Modifier = Modifier
        .padding(horizontal = 16.dp)
        .fillMaxWidth(),
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = if (isSelected) FutsalggColor.mint500 else FutsalggColor.mono200,
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                color = if (isSelected) FutsalggColor.mint50 else FutsalggColor.white,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                onClick()
            }
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .align(Alignment.Center),
            text = text,
            style = FutsalggTypography.bold_20_300,
            color = FutsalggColor.mono900
        )
    }
}