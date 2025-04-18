package com.futsalgg.app.presentation.match.updateround

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.futsalgg.app.R
import com.futsalgg.app.presentation.common.screen.BaseScreen
import com.futsalgg.app.presentation.match.MatchSharedViewModel
import com.futsalgg.app.ui.components.BottomButton
import com.futsalgg.app.ui.components.spacers.VerticalSpacer12
import com.futsalgg.app.ui.components.spacers.VerticalSpacer16
import com.futsalgg.app.ui.components.spacers.VerticalSpacer32
import com.futsalgg.app.ui.components.spacers.VerticalSpacer8
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun UpdateMatchRoundScreen(
    navController: NavController,
    viewModel: UpdateMatchRoundViewModel = hiltViewModel(),
    sharedViewModel: MatchSharedViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val matchState by sharedViewModel.matchState.collectAsState()
    val selectedIndex = remember { mutableStateOf<Int?>(null) }

    BaseScreen(
        title = matchState.type.toString(),
        navController = navController,
        uiState = uiState
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(FutsalggColor.white),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                VerticalSpacer32()

                Text(
                    text = stringResource(R.string.update_round_content_text),
                    style = FutsalggTypography.bold_24_400,
                    color = FutsalggColor.mono900
                )

                VerticalSpacer12()

                Text(
                    text = stringResource(R.string.update_round_sub_content_text),
                    style = FutsalggTypography.light_17_200,
                    color = FutsalggColor.mono900
                )

                VerticalSpacer16()
            }

            Column(
                modifier = Modifier.background(FutsalggColor.mono50)
            ) {
                HorizontalDivider(thickness = 1.dp, color = FutsalggColor.mono200)
                VerticalSpacer8()
            }

            LazyColumn(
                modifier = Modifier
                    .background(FutsalggColor.mono50)
                    .weight(1f)
            ) {
                items(9) { index ->
                    val round = index + 1
                    val borderColor = if (selectedIndex.value == index) {
                        FutsalggColor.mono900
                    } else {
                        FutsalggColor.mono200
                    }
                    val textColor = if (selectedIndex.value == index) {
                        FutsalggColor.mono900
                    } else {
                        FutsalggColor.mono500
                    }
                    Box(
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp,
                                vertical = 8.dp
                            )
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                width = 1.dp,
                                shape = RoundedCornerShape(8.dp),
                                color = borderColor
                            )
                            .clickable {
                                if (index == selectedIndex.value) {
                                    selectedIndex.value = null
                                } else {
                                    selectedIndex.value = index
                                }
                            }
                            .background(FutsalggColor.white)
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 12.dp)
                                .align(Alignment.Center),
                            text = "${round}판",
                            style = FutsalggTypography.bold_17_200,
                            color = textColor
                        )
                    }
                }
            }

            BottomButton(
                text = stringResource(R.string.start_text),
                enabled = selectedIndex.value != null,
                onClick = {
                    viewModel.updateRounds(
                        matchId = matchState.id,
                        rounds = selectedIndex.value!!.plus(1)
                    )
                    // TODO 마지막 버튼 클릭
                }
            )
        }
    }
}