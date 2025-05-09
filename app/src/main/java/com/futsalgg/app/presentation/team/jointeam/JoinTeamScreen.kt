package com.futsalgg.app.presentation.team.jointeam

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.futsalgg.app.R
import com.futsalgg.app.presentation.common.screen.BaseScreen
import com.futsalgg.app.ui.components.BottomButton
import com.futsalgg.app.ui.components.SimpleTitleText
import com.futsalgg.app.ui.components.spacers.VerticalSpacer8
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.futsalgg.app.navigation.RoutePath
import com.futsalgg.app.ui.components.DoubleButtons
import com.futsalgg.app.ui.components.SingleButton
import com.futsalgg.app.ui.components.spacers.VerticalSpacer12
import com.futsalgg.app.ui.components.spacers.VerticalSpacer16
import com.futsalgg.app.ui.components.spacers.VerticalSpacer32
import kotlin.math.roundToInt

@Composable
fun JoinTeamScreen(
    navController: NavController,
    viewModel: JoinTeamViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val state by viewModel.state.collectAsState()
    val name = state.name
    val buttonEnabled = state.buttonEnabled
    val searchResults = state.searchResults
    val selectedTeamId = state.selectedTeamId
    var showJoinDialog by remember { mutableStateOf(false) }
    var showCompleteDialog by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val searchSectionHeight = 108.dp
    val searchSectionHeightPx =
        with(LocalDensity.current) { searchSectionHeight.roundToPx().toFloat() }
    val searchSectionOffsetPx = remember { mutableFloatStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = searchSectionOffsetPx.floatValue + delta
                searchSectionOffsetPx.floatValue = newOffset.coerceIn(-searchSectionHeightPx, 0f)
                return Offset.Zero
            }
        }
    }

    BaseScreen(
        navController = navController,
        screenName = RoutePath.JOIN_TEAM,
        title = stringResource(R.string.join_team_text),
        uiState = uiState
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .nestedScroll(nestedScrollConnection)
                    .background(FutsalggColor.white)
                    .fillMaxSize()
            ) {
                item {
                    Spacer(Modifier.height(searchSectionHeight))
                }

                items(searchResults) { item ->
                    Spacer(Modifier.height(16.dp))

                    val isSelected = item.id == selectedTeamId

                    Box(
                        Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = if (isSelected) FutsalggColor.mint500 else FutsalggColor.mono300,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .background(
                                if (isSelected) FutsalggColor.mint50 else FutsalggColor.white,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                viewModel.onTeamSelected(item)
                            }
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = item.name,
                                style = FutsalggTypography.bold_20_300,
                                color = FutsalggColor.mono900
                            )
                            Spacer(Modifier.height(8.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = item.leaderName,
                                    style = FutsalggTypography.regular_17_200,
                                    color = if (isSelected) FutsalggColor.mint500 else FutsalggColor.mono500
                                )
                                Spacer(Modifier.width(8.dp))

                                Row(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(if (isSelected) FutsalggColor.mint300 else FutsalggColor.mono50),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Spacer(Modifier.width(4.dp))
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.ic_human_24),
                                        contentDescription = "",
                                        tint = if (isSelected) FutsalggColor.white else FutsalggColor.mono500
                                    )
                                    Text(
                                        text = item.memberCount,
                                        style = FutsalggTypography.regular_17_200,
                                        color = if (isSelected) FutsalggColor.white else FutsalggColor.mono500
                                    )
                                    Spacer(Modifier.width(8.dp))
                                }
                            }
                        }
                    }
                }

                item {
                    Spacer(Modifier.height(96.dp))
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .offset {
                        IntOffset(
                            x = 0,
                            y = searchSectionOffsetPx.floatValue.roundToInt()
                        )
                    }
                    .background(FutsalggColor.white)
                    .drawBehind {
                        drawRect(
                            color = FutsalggColor.mono900.copy(alpha = 0.1f),
                            topLeft = Offset(0f, size.height - 2.dp.toPx()),
                            size = Size(size.width, 2.dp.toPx())
                        )
                    }
            ) {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))

                    SimpleTitleText(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = stringResource(R.string.join_team_search_bar_title)
                    )
                    VerticalSpacer8()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .border(
                                width = 1.dp,
                                shape = RoundedCornerShape(8.dp),
                                color = FutsalggColor.mono500
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BasicTextField(
                            value = name,
                            onValueChange = viewModel::onNameChange,
                            modifier = Modifier
                                .weight(1f)
                                .padding(12.dp),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    viewModel.searchTeams(name)
                                    focusManager.clearFocus()
                                }
                            ),
                        )

                        Image(
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .clickable {
                                    viewModel.searchTeams(name)
                                    focusManager.clearFocus()
                                },
                            imageVector = ImageVector.vectorResource(R.drawable.ic_search_18),
                            contentDescription = ""
                        )
                    }

                    Spacer(Modifier.height(16.dp))
                }
            }

            BottomButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(FutsalggColor.white),
                text = stringResource(R.string.create_button_text),
                onClick = {
                    showJoinDialog = true
                },
                enabled = buttonEnabled
            )

            if (showJoinDialog) {
                JoinTeamDialog(
                    onConfirm = {
//                        showCompleteDialog = true
//                        showJoinDialog = false
                        viewModel.joinTeam(
                            state.selectedTeamId!!
                        ) {
                            showCompleteDialog = true
                            showJoinDialog = false
                        }
                    },
                    onDismiss = { showJoinDialog = false },
                    state = state
                )
            }

            if (showCompleteDialog) {
                ConfirmDialog(
                    onDismiss = {
                        showCompleteDialog = false
                    },
                    onConfirm = {
                        // TODO Click last confirm
                        showCompleteDialog = false
                    }
                )
            }
        }
    }
}

@Composable
fun JoinTeamDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    state: JoinTeamState
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .background(
                    color = FutsalggColor.white,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 32.dp)
                        .fillMaxWidth(),
                    text = stringResource(
                        R.string.join_team_dialog_title,
                        state.name
                    ),
                    style = FutsalggTypography.bold_20_300,
                    color = FutsalggColor.mono900,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DoubleButtons(
                        leftText = stringResource(R.string.cancel_text),
                        rightText = stringResource(R.string.apply_text),
                        onLeftClick = { onDismiss() },
                        onRightClick = {
                            onConfirm()
                        }
                    )
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ConfirmDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.background(
                    color = FutsalggColor.white
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                VerticalSpacer32()
                Text(
                    text = stringResource(R.string.join_team_confirmation_dialog_title),
                    style = FutsalggTypography.bold_20_300
                )

                VerticalSpacer12()

                Text(
                    text = stringResource(R.string.join_team_confirmation_dialog_sub_title),
                    style = FutsalggTypography.regular_17_200,
                    color = FutsalggColor.mono700,
                    textAlign = TextAlign.Center
                )
                VerticalSpacer16()
                SingleButton(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(R.string.join_team_confirmation_dialog_button_text),
                    onClick = {
                        onConfirm()
                    }
                )
            }
        }
    }
}