package com.futsalgg.app.di

import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.auth.repository.AuthRepository
import com.futsalgg.app.domain.team.usecase.CreateTeamUseCase
import com.futsalgg.app.domain.auth.usecase.AuthUseCase
import com.futsalgg.app.domain.user.usecase.CreateUserUseCase
import com.futsalgg.app.domain.team.usecase.CreateTeamUseCaseImpl
import com.futsalgg.app.domain.auth.usecase.AuthUseCaseImpl
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
import dagger.Binds
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
}