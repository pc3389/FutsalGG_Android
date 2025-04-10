package com.futsalgg.app.di

import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.auth.repository.AuthRepository
import com.futsalgg.app.domain.team.usecase.CreateTeamUseCase
import com.futsalgg.app.domain.auth.usecase.AuthUseCase
import com.futsalgg.app.domain.user.usecase.CreateUserUseCase
import com.futsalgg.app.domain.team.usecase.CreateTeamUseCaseImpl
import com.futsalgg.app.domain.auth.usecase.AuthUseCaseImpl
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
    fun bindSearchTeamsUseCase(
        teamRepository: TeamRepository
    ): SearchTeamsUseCase {
        return SearchTeamsUseCaseImpl(teamRepository)
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
}