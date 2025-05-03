package com.futsalgg.app.presentation.splash

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.futsalgg.app.R
import com.futsalgg.app.navigation.RoutePath
import com.futsalgg.app.presentation.common.error.getCause
import com.futsalgg.app.presentation.common.error.getCode
import com.futsalgg.app.presentation.common.error.getMessage
import com.futsalgg.app.presentation.common.error.getType
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.ui.theme.FutsalggColor

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset("lottie/splash_loading_bar.json")
    )

    val uiState by viewModel.uiState.collectAsState()

    val navState by viewModel.splashState.collectAsState()

    if (navState.toMain) {
        viewModel.splashStateToFalse()
        navController.navigate(RoutePath.MAIN) {
            popUpTo(0)
        }
    }

    if (navState.toLogin) {
        viewModel.splashStateToFalse()
        navController.navigate(RoutePath.LOGIN) {
            popUpTo(0)
        }
    }

    if (navState.toCreateUser) {
        viewModel.splashStateToFalse()
        navController.navigate(RoutePath.CREATE_USER) {
            popUpTo(0)
        }
    }

    if (navState.toSelectTeam) {
        viewModel.splashStateToFalse()
        navController.navigate(RoutePath.SELECT_TEAM) {
            popUpTo(0)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = FutsalggColor.mono900)
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.weight(2f))
            Image(
                modifier = Modifier.size(100.dp),
                imageVector = ImageVector.vectorResource(R.drawable.futsalgg_logo),
                contentDescription = ""
            )
            Spacer(Modifier.weight(3f))
        }
        LottieAnimation(
            composition = composition,
            iterations = Int.MAX_VALUE,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 80.dp)
                .align(Alignment.BottomCenter)
        )
    }

    if (uiState is UiState.Error) {
        Log.e(RoutePath.SPLASH, "${(uiState as UiState.Error).error.getType()} - [${(uiState as UiState.Error).error.getCode()}] ${(uiState as UiState.Error).error.getMessage()} ${(uiState as UiState.Error).error.getCause()}")
    }
}