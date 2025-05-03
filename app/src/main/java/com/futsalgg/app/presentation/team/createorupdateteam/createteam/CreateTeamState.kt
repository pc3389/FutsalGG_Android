package com.futsalgg.app.presentation.team.createteam

import android.graphics.Bitmap
import com.futsalgg.app.presentation.common.state.EditTextState
import com.futsalgg.app.presentation.team.model.Access
import com.futsalgg.app.presentation.common.model.MatchType

data class CreateTeamState(
    val teamName: String = "",
    val teamNameState: EditTextState = EditTextState.Initial,
//    val matchType: MatchType? = null, // TODO MVP 2때 활성화(매치전)
    val matchType: MatchType = MatchType.INTRA_SQUAD,
    val access: Access? = null,
    val introduction: String = "",
    val rule: String = "",
    val dues: String = "",
    val croppedTeamImage: Bitmap? = null,
    val teamImageUrl: String? = null,
    val errorMessage: String? = null
) {
    val isFormValid: Boolean
        get() = matchType != null && access != null &&
                teamNameState == EditTextState.Available &&
                dues.isNotEmpty()
}