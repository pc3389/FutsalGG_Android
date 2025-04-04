package com.futsalgg.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.credentials.CredentialManager
import androidx.navigation.compose.rememberNavController
import com.futsalgg.app.navigation.AppNavHost
import com.futsalgg.app.ui.theme.FutsalGG_AndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            FutsalGG_AndroidTheme {
                val navController = rememberNavController()
                val credentialManager = CredentialManager.create(this)
                AppNavHost(
                    navController = navController,
                    credentialManager = credentialManager
                )
            }
        }
    }
}