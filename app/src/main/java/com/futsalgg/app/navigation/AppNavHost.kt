package com.futsalgg.app.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.futsalgg.app.presentation.common.imagecrop.ProfileImageCropScreen
import com.futsalgg.app.presentation.auth.login.LoginScreen
import com.futsalgg.app.presentation.main.MainScreen
import com.futsalgg.app.presentation.user.createuser.CreateUserScreen
import com.futsalgg.app.presentation.user.createuser.CreateUserViewModel
import com.futsalgg.app.presentation.auth.termsandcondition.TermsAndConditionScreen
import com.futsalgg.app.presentation.match.result.MatchResultScreen
import com.futsalgg.app.presentation.team.createteam.CreateTeamScreen
import com.futsalgg.app.presentation.team.createteam.CreateTeamViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    credentialManager: CredentialManager
) {
    NavHost(
        navController = navController,
//        startDestination = Screen.Login.route
//        startDestination = Screen.CreateUser.route
//        startDestination = Screen.CreateTeam.route
        startDestination = Screen.MatchResult.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                navController = navController,
                onClick = {
                    //TODO On Login Click
                },
                credentialManager = credentialManager,
                context = LocalContext.current,
                onLoginSuccess = {
                    // TODO On Login Success
                }
            )
        }
        composable(Screen.Main.route) { MainScreen(navController) }
        composable(Screen.TermsAndCondition.route) { TermsAndConditionScreen() }
        composable(Screen.CreateUser.route) {
            CreateUserScreen(navController)
        }
        composable(Screen.CreateTeam.route) {
            CreateTeamScreen(navController)
        }
        composable(Screen.MatchResult.route) {
            MatchResultScreen(navController)
        }
        composable(
            route = "cropImage?uri={uri}&viewModelType={viewModelType}",
            arguments = listOf(
                navArgument("uri") { type = NavType.StringType },
                navArgument("viewModelType") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val uriStr = backStackEntry.arguments?.getString("uri") ?: ""
            val viewModelType = backStackEntry.arguments?.getString("viewModelType") ?: ""
            val uri = Uri.parse(uriStr)

            when (viewModelType) {
                RoutePath.CREATE_USER -> {
                    val parentEntry = navController.getBackStackEntry(RoutePath.CREATE_USER)
                    val viewModel: CreateUserViewModel = hiltViewModel(parentEntry)
                    ProfileImageCropScreen(
                        imageUri = uri,
                        onBack = { navController.popBackStack() },
                        onConfirm = { bitmap ->
                            viewModel.setCroppedImage(bitmap)
                            navController.popBackStack()
                        }
                    )
                }
                RoutePath.CREATE_TEAM -> {
                    val parentEntry = navController.getBackStackEntry(RoutePath.CREATE_TEAM)
                    val viewModel: CreateTeamViewModel = hiltViewModel(parentEntry)
                    ProfileImageCropScreen(
                        imageUri = uri,
                        onBack = { navController.popBackStack() },
                        onConfirm = { bitmap ->
                            viewModel.setCroppedImage(bitmap)
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}