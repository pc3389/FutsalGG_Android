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
import com.futsalgg.app.presentation.match.create.CreateMatchScreen
import com.futsalgg.app.presentation.match.creatematchparticipants.CreateMatchParticipantsScreen
import com.futsalgg.app.presentation.match.matchstat.checkmatchstat.CheckMatchStatScreen
import com.futsalgg.app.presentation.match.matchstat.updatematchstat.UpdateMatchStatScreen
import com.futsalgg.app.presentation.match.result.MatchResultScreen
import com.futsalgg.app.presentation.match.updateround.UpdateMatchRoundScreen
import com.futsalgg.app.presentation.match.updatesubteam.UpdateMatchParticipantsSubTeamScreen
import com.futsalgg.app.presentation.setting.SettingScreen
import com.futsalgg.app.presentation.team.createteam.CreateTeamScreen
import com.futsalgg.app.presentation.team.createteam.CreateTeamViewModel
import com.futsalgg.app.presentation.team.jointeam.JoinTeamScreen
import com.futsalgg.app.presentation.team.selectteam.SelectTeamScreen
import com.futsalgg.app.presentation.teammember.profilecard.MyProfileScreen
import com.futsalgg.app.presentation.user.updateprofile.UpdateProfileScreen
import com.futsalgg.app.presentation.user.updateprofile.UpdateProfileViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    credentialManager: CredentialManager
) {
    NavHost(
        navController = navController,
//        startDestination = Screen.Login.route
//        startDestination = Screen.TermsAndCondition.route
//        startDestination = Screen.CreateUser.route
        startDestination = Screen.SelectTeam.route
//        startDestination = Screen.CreateTeam.route
//        startDestination = Screen.MatchResult.route
//        startDestination = Screen.CreateMatch.route
//        startDestination = Screen.JoinTeam.route
//        startDestination = Screen.Main.route
//        startDestination = Screen.MyProfile.route
//        startDestination = Screen.Setting.route
//        startDestination = Screen.UpdateProfile.route
//        startDestination = Screen.CreateMatchMemberScreen.route
//        startDestination = Screen.UpdateMatchParticipantsSubTeamScreen.route
//        startDestination = Screen.UpdateMatchRoundScreen.route
//        startDestination = Screen.CheckMatchStatScreen.route
//        startDestination = Screen.UpdateMatchStatScreen.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                credentialManager = credentialManager,
                context = LocalContext.current,
                onLoginSuccess = {
                    navController.navigate(Screen.TermsAndCondition.route)
                }
            )
        }
        composable(Screen.Main.route) { MainScreen(navController) }
        composable(Screen.TermsAndCondition.route) {
            TermsAndConditionScreen(navController)
        }
        composable(Screen.CreateUser.route) {
            CreateUserScreen(navController)
        }
        composable(Screen.SelectTeam.route) {
            SelectTeamScreen(navController)
        }
        composable(Screen.CreateTeam.route) {
            CreateTeamScreen(navController)
        }
        composable(Screen.MatchResult.route) {
            MatchResultScreen(navController)
        }
        composable(Screen.CreateMatch.route) {
            CreateMatchScreen(navController)
        }
        composable(Screen.JoinTeam.route) {
            JoinTeamScreen(navController)
        }
        composable(Screen.MyProfile.route) {
            MyProfileScreen(navController)
        }
        composable(Screen.Setting.route) {
            SettingScreen(navController)
        }
        composable(Screen.UpdateProfile.route) {
            UpdateProfileScreen(navController)
        }
        composable(Screen.CreateMatchMemberScreen.route) {
            CreateMatchParticipantsScreen(navController)
        }
        composable(Screen.UpdateMatchParticipantsSubTeamScreen.route) {
            UpdateMatchParticipantsSubTeamScreen(navController)
        }
        composable(Screen.UpdateMatchRoundScreen.route) {
            UpdateMatchRoundScreen(navController)
        }
        composable(Screen.CheckMatchStatScreen.route) {
            CheckMatchStatScreen(navController)
        }
        composable(Screen.UpdateMatchStatScreen.route) {
            UpdateMatchStatScreen(navController)
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

                RoutePath.UPDATE_PROFILE -> {
                    val parentEntry = navController.getBackStackEntry(RoutePath.UPDATE_PROFILE)
                    val viewModel: UpdateProfileViewModel = hiltViewModel(parentEntry)
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