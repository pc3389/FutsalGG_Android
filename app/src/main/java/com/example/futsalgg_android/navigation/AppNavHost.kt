package com.example.futsalgg_android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.futsalgg_android.ui.login.LoginScreen
import com.example.futsalgg_android.ui.main.MainScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
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
    }
}