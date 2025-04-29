package com.futsalgg.app.di

import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.auth.repository.AuthRepository
import com.futsalgg.app.domain.team.usecase.CreateTeamUseCase
import com.futsalgg.app.domain.auth.usecase.AuthUseCase
import com.futsalgg.app.domain.user.usecase.CreateUserUseCase
import com.futsalgg.app.domain.team.usecase.CreateTeamUseCaseImpl
import com.futsalgg.app.domain.auth.usecase.AuthUseCaseImpl
import com.futsalgg.app.domain.auth.usecase.RefreshTokenUseCase
import com.futsalgg.app.domain.auth.usecase.RefreshTokenUseCaseImpl
import com.futsalgg.app.domain.match.repository.MatchParticipantRepository
import com.futsalgg.app.domain.match.repository.MatchRepository
import com.futsalgg.app.domain.team.repository.TeamRepository
import com.futsalgg.app.domain.user.repository.UserRepository
import com.futsalgg.app.domain.user.usecase.CreateUserUseCaseImpl
import com.futsalgg.app.domain.match.usecase.CreateMatchUseCase
import com.futsalgg.app.domain.match.usecase.CreateMatchUseCaseImpl
import com.futsalgg.app.domain.match.usecase.DeleteMatchUseCase
import com.futsalgg.app.domain.match.usecase.DeleteMatchUseCaseImpl
import com.futsalgg.app.domain.match.usecase.GetMatchesUseCase
import com.futsalgg.app.domain.match.usecase.GetMatchesUseCaseImpl
import com.futsalgg.app.domain.team.usecase.SearchTeamsUseCase
import com.futsalgg.app.domain.team.usecase.SearchTeamsUseCaseImpl
import com.futsalgg.app.domain.team.repository.TeamMemberRepository
import com.futsalgg.app.domain.team.usecase.JoinTeamUseCase
import com.futsalgg.app.domain.team.usecase.JoinTeamUseCaseImpl
import com.futsalgg.app.domain.team.usecase.GetMyTeamUseCase
import com.futsalgg.app.domain.team.usecase.GetMyTeamUseCaseImpl
import com.futsalgg.app.domain.user.usecase.GetMyProfileForSettingUseCase
import com.futsalgg.app.domain.user.usecase.GetMyProfileForSettingUseCaseImpl
import com.futsalgg.app.domain.user.usecase.UpdateNotificationUseCase
import com.futsalgg.app.domain.user.usecase.UpdateNotificationUseCaseImpl
import com.futsalgg.app.domain.user.usecase.UpdateProfileUseCase
import com.futsalgg.app.domain.user.usecase.UpdateProfileUseCaseImpl
import com.futsalgg.app.domain.team.usecase.GetTeamMemberForProfileUseCase
import com.futsalgg.app.domain.team.usecase.GetTeamMemberForProfileUseCaseImpl
import com.futsalgg.app.domain.match.usecase.CreateMatchParticipantsUseCase
import com.futsalgg.app.domain.match.usecase.CreateMatchParticipantsUseCaseImpl
import com.futsalgg.app.domain.match.usecase.GetMatchUseCase
import com.futsalgg.app.domain.match.usecase.GetMatchUseCaseImpl
import com.futsalgg.app.domain.team.usecase.GetTeamMembersUseCase
import com.futsalgg.app.domain.team.usecase.GetTeamMembersUseCaseImpl
import com.futsalgg.app.domain.match.usecase.UpdateMatchParticipantsSubTeamUseCase
import com.futsalgg.app.domain.match.usecase.UpdateMatchParticipantsSubTeamUseCaseImpl
import com.futsalgg.app.domain.match.usecase.UpdateMatchRoundsUseCase
import com.futsalgg.app.domain.match.usecase.UpdateMatchRoundsUseCaseImpl
import com.futsalgg.app.domain.match.usecase.GetMatchParticipantsUseCase
import com.futsalgg.app.domain.match.usecase.GetMatchParticipantsUseCaseImpl
import com.futsalgg.app.domain.match.usecase.GetMatchStatsUseCase
import com.futsalgg.app.domain.match.usecase.GetMatchStatsUseCaseImpl
import com.futsalgg.app.domain.match.usecase.CreateMatchStatUseCase
import com.futsalgg.app.domain.match.usecase.CreateMatchStatUseCaseImpl
import com.futsalgg.app.domain.user.usecase.UploadUserProfilePictureUseCase
import com.futsalgg.app.domain.user.usecase.UploadUserProfilePictureUseCaseImpl
import com.futsalgg.app.domain.team.usecase.GetTeamMembersByTeamIdUseCase
import com.futsalgg.app.domain.team.usecase.GetTeamMembersByTeamIdUseCaseImpl
import com.futsalgg.app.domain.match.usecase.GetRecentMatchDateUseCase
import com.futsalgg.app.domain.match.usecase.GetRecentMatchDateUseCaseImpl
import com.futsalgg.app.domain.team.usecase.AcceptTeamMemberUseCase
import com.futsalgg.app.domain.team.usecase.AcceptTeamMemberUseCaseImpl
import com.futsalgg.app.domain.team.usecase.RejectTeamMemberUseCase
import com.futsalgg.app.domain.team.usecase.RejectTeamMemberUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideAuthUseCase(
        authRepository: AuthRepository,
        tokenManager: ITokenManager
    ): AuthUseCase {
        return AuthUseCaseImpl(authRepository, tokenManager)
    }

    @Provides
    @Singleton
    fun provideSignupUseCase(
        userRepository: UserRepository
    ): CreateUserUseCase {
        return CreateUserUseCaseImpl(userRepository)
    }

    @Provides
    @Singleton
    fun provideCreateTeamUseCase(
        teamRepository: TeamRepository
    ): CreateTeamUseCase {
        return CreateTeamUseCaseImpl(teamRepository)
    }

    @Provides
    @Singleton
    fun provideSearchTeamsUseCase(
        teamRepository: TeamRepository
    ): SearchTeamsUseCase {
        return SearchTeamsUseCaseImpl(teamRepository)
    }

    @Provides
    @Singleton
    fun provideJoinTeamUseCase(
        teamMemberRepository: TeamMemberRepository
    ): JoinTeamUseCase {
        return JoinTeamUseCaseImpl(teamMemberRepository)
    }

    @Provides
    @Singleton
    fun provideGetMatchesUseCase(
        matchRepository: MatchRepository
    ): GetMatchesUseCase {
        return GetMatchesUseCaseImpl(matchRepository)
    }

    @Provides
    @Singleton
    fun provideCreateMatchUseCase(
        matchRepository: MatchRepository
    ): CreateMatchUseCase {
        return CreateMatchUseCaseImpl(matchRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteMatchUseCase(
        matchRepository: MatchRepository
    ): DeleteMatchUseCase {
        return DeleteMatchUseCaseImpl(matchRepository)
    }

    @Provides
    @Singleton
    fun provideGetMyTeamUseCase(
        teamRepository: TeamRepository
    ): GetMyTeamUseCase {
        return GetMyTeamUseCaseImpl(teamRepository)
    }

    @Provides
    @Singleton
    fun provideGetMyProfileForSettingUseCase(
        userRepository: UserRepository
    ): GetMyProfileForSettingUseCase {
        return GetMyProfileForSettingUseCaseImpl(userRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateNotificationUseCase(
        userRepository: UserRepository
    ): UpdateNotificationUseCase {
        return UpdateNotificationUseCaseImpl(userRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateProfileUseCase(
        userRepository: UserRepository
    ): UpdateProfileUseCase {
        return UpdateProfileUseCaseImpl(userRepository)
    }

    @Provides
    @Singleton
    fun provideGetTeamMemberForProfileUseCase(
        teamMemberRepository: TeamMemberRepository
    ): GetTeamMemberForProfileUseCase {
        return GetTeamMemberForProfileUseCaseImpl(teamMemberRepository)
    }

    @Provides
    @Singleton
    fun provideCreateMatchParticipantsUseCase(
        matchParticipantRepository: MatchParticipantRepository
    ): CreateMatchParticipantsUseCase {
        return CreateMatchParticipantsUseCaseImpl(matchParticipantRepository)
    }

    @Provides
    @Singleton
    fun provideGetMatchUseCase(
        matchRepository: MatchRepository
    ): GetMatchUseCase {
        return GetMatchUseCaseImpl(matchRepository)
    }

    @Provides
    @Singleton
    fun provideGetTeamMemberUseCase(
        teamMemberRepository: TeamMemberRepository
    ): GetTeamMembersUseCase {
        return GetTeamMembersUseCaseImpl(teamMemberRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateMatchParticipantsSubTeamUseCase(
        matchParticipantRepository: MatchParticipantRepository
    ): UpdateMatchParticipantsSubTeamUseCase {
        return UpdateMatchParticipantsSubTeamUseCaseImpl(matchParticipantRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateMatchRoundsUseCase(
        matchRepository: MatchRepository
    ): UpdateMatchRoundsUseCase {
        return UpdateMatchRoundsUseCaseImpl(matchRepository)
    }

    @Singleton
    @Provides
    fun provideGetMatchParticipantsUseCase(
        matchParticipantRepository: MatchParticipantRepository
    ): GetMatchParticipantsUseCase {
        return GetMatchParticipantsUseCaseImpl(matchParticipantRepository)
    }

    @Singleton
    @Provides
    fun provideGetMatchStatsUseCase(
        matchRepository: MatchRepository
    ): GetMatchStatsUseCase {
        return GetMatchStatsUseCaseImpl(matchRepository)
    }

    @Singleton
    @Provides
    fun provideCreateMatchStatUseCase(
        matchRepository: MatchRepository
    ): CreateMatchStatUseCase {
        return CreateMatchStatUseCaseImpl(matchRepository)
    }

    @Singleton
    @Provides
    fun provideUploadUserProfilePictureUseCase(
        userRepository: UserRepository
    ): UploadUserProfilePictureUseCase {
        return UploadUserProfilePictureUseCaseImpl(userRepository)
    }

    @Provides
    @Singleton
    fun provideGetTeamMembersByTeamIdUseCase(
        teamMemberRepository: TeamMemberRepository
    ): GetTeamMembersByTeamIdUseCase {
        return GetTeamMembersByTeamIdUseCaseImpl(teamMemberRepository)
    }

    @Provides
    @Singleton
    fun provideRefreshTokenUseCase(
        authRepository: AuthRepository
    ): RefreshTokenUseCase {
        return RefreshTokenUseCaseImpl(authRepository)
    }

    @Provides
    @Singleton
    fun provideGetRecentMatchDateUseCase(
        matchRepository: MatchRepository
    ): GetRecentMatchDateUseCase {
        return GetRecentMatchDateUseCaseImpl(matchRepository)
    }

    @Provides
    @Singleton
    fun provideAcceptTeamMemberUseCase(
        teamRepository: TeamRepository
    ): AcceptTeamMemberUseCase {
        return AcceptTeamMemberUseCaseImpl(teamRepository)
    }

    @Provides
    @Singleton
    fun provideRejectTeamMemberUseCase(
        teamRepository: TeamRepository
    ): RejectTeamMemberUseCase {
        return RejectTeamMemberUseCaseImpl(teamRepository)
    }
}