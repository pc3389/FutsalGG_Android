package com.futsalgg.app.navigation

sealed class Screen(val route: String) {
    object Login: Screen(RoutePath.LOGIN)
    object Main: Screen(RoutePath.MAIN)
    object TermsAndCondition: Screen(RoutePath.TERMS)
    object CreateUser: Screen(RoutePath.CREATE_USER)
    object CreateTeam: Screen(RoutePath.CREATE_TEAM)
    object MatchResult: Screen(RoutePath.MATCH_RESULT)
    object CreateMatch: Screen(RoutePath.CREATE_MATCH)
    object JoinTeam: Screen(RoutePath.JOIN_TEAM)
}