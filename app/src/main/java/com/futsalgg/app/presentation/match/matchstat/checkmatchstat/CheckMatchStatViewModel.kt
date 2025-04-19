package com.futsalgg.app.presentation.match.matchstat.checkmatchstat

import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.match.usecase.GetMatchParticipantsUseCase
import com.futsalgg.app.domain.match.usecase.GetMatchStatsUseCase
import com.futsalgg.app.presentation.match.matchstat.base.MatchStatBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CheckMatchStatViewModel @Inject constructor(
    getMatchStatsUseCase: GetMatchStatsUseCase,
    getMatchParticipantsUseCase: GetMatchParticipantsUseCase,
    tokenManager: ITokenManager
) : MatchStatBaseViewModel(
    getMatchStatsUseCase,
    getMatchParticipantsUseCase,
    tokenManager
) {
    init {
        super.initial{}
    }
}