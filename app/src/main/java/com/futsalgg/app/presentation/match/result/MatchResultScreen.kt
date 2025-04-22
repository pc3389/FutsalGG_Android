package com.futsalgg.app.presentation.match.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.futsalgg.app.R
import com.futsalgg.app.navigation.RoutePath
import com.futsalgg.app.presentation.common.screen.BaseScreen
import com.futsalgg.app.presentation.match.result.component.MatchResultPerDay
import com.futsalgg.app.ui.theme.FutsalggColor

@Composable
fun MatchResultScreen(
    navController: NavController,
    viewModel: MatchResultViewModel = hiltViewModel(
        navController.getBackStackEntry(RoutePath.MAIN)
    )
) {
    val matchesByDate by viewModel.matchesByDate.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    BaseScreen(
        navController = navController,
        screenName = RoutePath.MATCH_RESULT,
        title = stringResource(R.string.match_result_title_text),
        uiState = uiState
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(FutsalggColor.white)
                .padding(innerPadding)
        ) {
            VerticalDivider(
                thickness = 2.dp,
                modifier = Modifier.padding(start = 19.dp)
            )

            LazyColumn(
                modifier = Modifier.padding(top = 16.dp)
            ) {
                items(matchesByDate.entries.toList()) { (date, matches) ->
                    MatchResultPerDay(
                        date = date,
                        matches = matches,
                        onResultClick = { match ->
                            // TODO 경기결과확인 클릭
                            viewModel.updateMatch(match)
                        },
                        onScheduleEditClick = { match ->
                            viewModel.updateMatch(match)
                            navController.navigate(RoutePath.UPDATE_MATCH)
                        },
                        onResultEditClick = { match ->
                            viewModel.updateMatch(match)
                        },
                        onDeleteClick = { match ->

                        }
                    )
                }
            }
        }
    }
}