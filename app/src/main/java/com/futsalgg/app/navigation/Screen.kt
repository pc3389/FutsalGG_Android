package com.futsalgg.app.navigation

sealed class Screen(val route: String) {
    object Login: Screen(RoutePath.LOGIN)
    object Main: Screen(RoutePath.MAIN)
    object TermsAndCondition: Screen(RoutePath.TERMS)
    object CreateUser: Screen(RoutePath.CREATE_USER)
    object SelectTeam: Screen(RoutePath.SELECT_TEAM)
    object CreateTeam: Screen(RoutePath.CREATE_TEAM)
    object MatchResult: Screen(RoutePath.MATCH_RESULT)
    object CreateMatch: Screen(RoutePath.CREATE_MATCH)
    object JoinTeam: Screen(RoutePath.JOIN_TEAM)
    object ProfileCard: Screen(RoutePath.PROFILE_CARD)
    object Setting: Screen(RoutePath.SETTING)
    object UpdateProfile: Screen(RoutePath.UPDATE_PROFILE)
    object CreateMatchMemberScreen: Screen(RoutePath.CREATE_MATCH_PARTICIPANTS)
    object UpdateMatchParticipantsSubTeamScreen: Screen(RoutePath.UPDATE_MATCH_PARTICIPANTS_SUB_TEAM)
    object UpdateMatchRoundScreen: Screen(RoutePath.UPDATE_MATCH_ROUND)
    object CheckMatchStatScreen: Screen(RoutePath.CHECK_MATCH_STAT)
    object UpdateMatchStatScreen: Screen(RoutePath.UPDATE_MATCH_STAT)
    object Splash: Screen(RoutePath.SPLASH)
}