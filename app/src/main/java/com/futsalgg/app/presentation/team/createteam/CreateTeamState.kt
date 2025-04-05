package com.futsalgg.app.presentation.team.createteam

import android.graphics.Bitmap
import com.futsalgg.app.presentation.common.state.EditTextState
import com.futsalgg.app.presentation.team.model.Access
import com.futsalgg.app.presentation.team.model.MatchType

data class CreateTeamState(
    val teamName: String = "",
    val teamNameState: EditTextState = EditTextState.Initial,
    val matchType: MatchType = MatchType.ALL,
    val access: Access = Access.TEAM_LEADER,
    val introduction: String = "",
    val rule: String = "",
    val dues: String = "",
    val croppedTeamImage: Bitmap? = null,
    val teamImageUrl: String? = null,
    val errorMessage: String? = null
) 