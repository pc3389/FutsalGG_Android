package com.futsalgg.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.futsalgg.app.presentation.login.LoginScreen
import com.futsalgg.app.presentation.main.MainScreen
import com.futsalgg.app.presentation.signup.SignupScreen
import com.futsalgg.app.presentation.termsandcondition.TermsAndConditionScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    credentialManager: CredentialManager
) {
    NavHost(
        navController = navController,
//        startDestination = Screen.Login.route
        startDestination = Screen.Signup.route
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
        composable(Screen.Signup.route) {
            SignupScreen(navController)
        }
    }
}