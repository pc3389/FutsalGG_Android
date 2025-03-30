package com.futsalgg.app.navigation

sealed class Screen(val route: String) {
    object Login: Screen(RoutePath.LOGIN)
    object Main: Screen(RoutePath.MAIN)
    object TermsAndCondition: Screen(RoutePath.TERMS)
    object Signup: Screen(RoutePath.SIGNUP)
}