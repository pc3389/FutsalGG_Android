package com.futsalgg.app.presentation.match.matchitem.create

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.futsalgg.app.R
import com.futsalgg.app.navigation.RoutePath
import com.futsalgg.app.presentation.common.model.MatchType
import com.futsalgg.app.ui.components.DateInputField
import com.futsalgg.app.presentation.common.screen.BaseScreen
import com.futsalgg.app.presentation.common.state.DateState
import com.futsalgg.app.presentation.match.matchitem.BaseMatchScreen
import com.futsalgg.app.presentation.match.matchitem.create.component.TimeSelectorItem
import com.futsalgg.app.ui.components.BottomButton
import com.futsalgg.app.ui.components.EditTextBox
import com.futsalgg.app.ui.components.FormRequiredAndHeader
import com.futsalgg.app.ui.components.SimpleTitleText
import com.futsalgg.app.ui.components.TextWithStar
import com.futsalgg.app.ui.components.spacers.VerticalSpacer56
import com.futsalgg.app.ui.components.spacers.VerticalSpacer8
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun CreateMatchScreen(
    navController: NavController,
    viewModel: CreateMatchViewModel = hiltViewModel()
) {

    BaseMatchScreen(
        navController = navController,
        viewModel = viewModel,
        onBottomButtonClick = {
            viewModel.createMatch(
                onSuccess = {
                    navController.navigate(RoutePath.MATCH_RESULT) {
                        popUpTo(route = RoutePath.MAIN)
                    }
                }
            )
        }
    )
}