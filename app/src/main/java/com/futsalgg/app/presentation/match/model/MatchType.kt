package com.futsalgg.app.presentation.match.model

enum class MatchType {
    INTRA_SQUAD,
    INTER_TEAM;

    override fun toString(): String {
        return when(this) {
            INTRA_SQUAD -> "자체전 (내전)"
            INTER_TEAM -> "매치전 (vs)"
        }
    }
}