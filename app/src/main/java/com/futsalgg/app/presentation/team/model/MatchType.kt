package com.futsalgg.app.presentation.team.model

enum class MatchType {
    SELF_MATCH, // 자체전(내전)
    VS_MATCH,   // 매치전(VS)
    ALL;        // 모두

    override fun toString(): String {
        return when (this) {
            SELF_MATCH -> "자체전(내전)"
            VS_MATCH -> "매치전(VS)"
            ALL -> "모두"
        }
    }
} 