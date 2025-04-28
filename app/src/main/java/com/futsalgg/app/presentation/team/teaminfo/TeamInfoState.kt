package com.futsalgg.app.presentation.team.teaminfo

import com.futsalgg.app.domain.team.model.TeamMember as DomainTeamMember
import com.futsalgg.app.presentation.common.model.Gender
import com.futsalgg.app.presentation.common.model.TeamRole
import com.futsalgg.app.presentation.common.model.TeamRole.Companion.toDomain
import com.futsalgg.app.presentation.common.model.fromDomain
import com.futsalgg.app.presentation.common.model.toDomain
import com.futsalgg.app.presentation.team.teaminfo.TeamMemberState.TeamMemberStatus.Companion.fromDomain
import com.futsalgg.app.presentation.team.teaminfo.TeamMemberState.TeamMemberStatus.Companion.toDomain
import com.futsalgg.app.domain.team.model.TeamMemberStatus as DomainTeamMemberStatus

data class TeamMemberState(
    val id: String = "",
    val name: String = "닉네임",
    val role: TeamRole = TeamRole.TEAM_MEMBER,
    val profileUrl: String = "",
    val birthDate: String = "",
    val generation: String = "20대",
    val gender: Gender = Gender.NONE,
    val status: TeamMemberStatus = TeamMemberStatus.ACTIVE,
    val createdTime: String = "",
    val teamId: String = ""
) {
    enum class TeamMemberStatus {
        PENDING,
        ACTIVE,
        INACTIVE;

        companion object {
            fun DomainTeamMemberStatus.fromDomain(): TeamMemberStatus {
                return when (this) {
                    DomainTeamMemberStatus.PENDING -> PENDING
                    DomainTeamMemberStatus.ACTIVE -> ACTIVE
                    DomainTeamMemberStatus.INACTIVE -> INACTIVE
                }
            }

            fun TeamMemberStatus.toDomain(): DomainTeamMemberStatus {
                return when (this) {
                    PENDING -> DomainTeamMemberStatus.PENDING
                    ACTIVE -> DomainTeamMemberStatus.ACTIVE
                    INACTIVE -> DomainTeamMemberStatus.INACTIVE
                }
            }
        }
    }
    companion object {
        fun fromDomain(domain: DomainTeamMember): TeamMemberState {
            return TeamMemberState(
                id = domain.id,
                name = domain.name,
                role = TeamRole.fromDomain(domain.role),
                profileUrl = domain.profileUrl ?: "",
                birthDate = domain.birthDate,
                generation = domain.generation,
                gender = domain.gender.fromDomain(),
                status = domain.status.fromDomain(),
                createdTime = domain.createdTime,
                teamId = domain.teamId
            )
        }

        fun toDomain(presentation: TeamMemberState): DomainTeamMember {
            return DomainTeamMember(
                id = presentation.id,
                name = presentation.name,
                role = presentation.role.toDomain(),
                profileUrl = presentation.profileUrl,
                birthDate = presentation.birthDate,
                generation = presentation.generation,
                gender = presentation.gender.toDomain(),
                status = presentation.status.toDomain(),
                createdTime = presentation.createdTime,
                teamId = presentation.teamId
            )
        }
    }
}