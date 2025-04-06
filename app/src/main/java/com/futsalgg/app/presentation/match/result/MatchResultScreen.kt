package com.futsalgg.app.presentation.match.result

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.futsalgg.app.presentation.common.screen.BaseScreen
import com.futsalgg.app.presentation.match.model.Match
import com.futsalgg.app.presentation.match.model.MatchStatus
import com.futsalgg.app.presentation.match.model.MatchType
import com.futsalgg.app.presentation.match.model.VoteStatus
import com.futsalgg.app.presentation.match.result.component.MatchResultPerDay

@Composable
fun MatchResultScreen(
    navController: NavController,
    onBackClick: () -> Unit
) {
    BaseScreen(
        navController = navController,
        title = "경기 결과"
    ) { innerPadding ->
        MatchResultPerDay(
            innerPadding = innerPadding,
            date = "05.31",
            matches = listOf(
                Match(
                    id = "1123123123",
                    opponentTeamName = "상대팀",
                    description = "팀",
                    type = MatchType.INTER_TEAM,
                    matchDate = "05.26",
                    startTime = "05:12",
                    endTime = "05:12",
                    location = "Location",
                    voteStatus = VoteStatus.NONE,
                    status = MatchStatus.COMPLETED,
                    createdTime = "TODO()"
                ), Match(
                    id = "1123123123",
                    opponentTeamName = null,
                    description = "Description",
                    type = MatchType.INTRA_SQUAD,
                    matchDate = "05.26",
                    startTime = "05:12",
                    endTime = "05:12",
                    location = "Location",
                    voteStatus = VoteStatus.NONE,
                    status = MatchStatus.COMPLETED,
                    createdTime = "TODO()"
                )
            ),
            {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MatchResultScreenPreview() {
    MatchResultScreen(
        onBackClick = {},
        navController = rememberNavController()
    )
} 