package com.futsalgg.app.domain.team.repository

import com.futsalgg.app.domain.team.model.SearchTeamResponseModel
import com.futsalgg.app.domain.team.model.TeamLogoPresignedUrlResponseModel
import com.futsalgg.app.domain.team.model.TeamLogoResponseModel
import com.futsalgg.app.domain.team.model.MyTeam
import com.futsalgg.app.domain.common.model.MatchType
import com.futsalgg.app.domain.team.model.Access
import java.io.File

interface TeamRepository {
    suspend fun isTeamNicknameUnique(nickname: String): Result<Boolean>
    
    suspend fun getTeamLogoPresignedUrl(accessToken: String): Result<TeamLogoPresignedUrlResponseModel>
    
    suspend fun updateTeamLogo(accessToken: String, uri: String): Result<TeamLogoResponseModel>

    suspend fun uploadLogoImage(accessToken: String, file: File): Result<TeamLogoResponseModel>
    
    suspend fun createTeam(
        accessToken: String,
        name: String,
        introduction: String,
        rule: String,
        matchType: String,
        access: String,
        dues: Int
    ): Result<Unit>

    suspend fun searchTeams(name: String): Result<SearchTeamResponseModel>

    suspend fun getMyTeam(accessToken: String): Result<MyTeam>

    suspend fun acceptTeamMember(
        accessToken: String,
        teamMemberId: String
    ): Result<Unit>

    suspend fun rejectTeamMember(
        accessToken: String,
        teamMemberId: String
    ): Result<Unit>

    suspend fun updateTeam(
        accessToken: String,
        teamId: String,
        name: String,
        introduction: String,
        rule: String,
        matchType: MatchType,
        access: Access,
        dues: Int
    ): Result<Unit>
} 