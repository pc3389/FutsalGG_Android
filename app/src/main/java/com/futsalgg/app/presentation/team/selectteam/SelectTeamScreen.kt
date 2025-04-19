package com.futsalgg.app.presentation.team.selectteam

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.futsalgg.app.R
import com.futsalgg.app.navigation.RoutePath
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun SelectTeamScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box (
            modifier = Modifier.fillMaxWidth()
                .weight(1f)
                .clickable {
                    navController.navigate(RoutePath.CREATE_TEAM)
                }
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.img_select_team_top_bg),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = stringResource(R.string.create_team),
                style = FutsalggTypography.bold_24_400,
                color = FutsalggColor.white,
                modifier = Modifier.align(Alignment.BottomCenter)
                    .padding(bottom = 80.dp)
            )
        }
        Box (
            modifier = Modifier.fillMaxWidth()
                .weight(1f)
                .clickable {
                    navController.navigate(RoutePath.JOIN_TEAM)
                }
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.img_select_team_bg_bottom),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = stringResource(R.string.join_team_text),
                style = FutsalggTypography.bold_24_400,
                color = FutsalggColor.mono900,
                modifier = Modifier.align(Alignment.TopCenter)
                    .padding(top = 80.dp)
            )
        }
    }

}