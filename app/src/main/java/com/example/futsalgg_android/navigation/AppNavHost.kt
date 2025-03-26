package com.example.futsalgg_android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.futsalgg_android.ui.login.LoginScreen
import com.example.futsalgg_android.ui.main.MainScreen
import com.example.futsalgg_android.ui.termsandcondition.TermsAndConditionScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.TermsAndCondition.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                navController,
                onClick = {
                    //TODO On Login Click
                },
                context = LocalContext.current
            )
        }
        composable(Screen.Main.route) { MainScreen(navController) }
        composable(Screen.TermsAndCondition.route) { TermsAndConditionScreen() }
    }
}