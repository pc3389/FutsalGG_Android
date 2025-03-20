package com.example.futsalgg_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.futsalgg_android.navigation.AppNavHost
import com.example.futsalgg_android.ui.theme.FutsalGG_AndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FutsalGG_AndroidTheme {
                val navController = rememberNavController()
                AppNavHost(navController)
            }
        }
    }
}