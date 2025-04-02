package com.futsalgg.app.di

import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.auth.repository.AuthRepository
import com.futsalgg.app.domain.team.usecase.CreateTeamUseCase
import com.futsalgg.app.domain.auth.usecase.AuthUseCase
import com.futsalgg.app.domain.user.usecase.SignupUseCase
import com.futsalgg.app.domain.team.usecase.CreateTeamUseCaseImpl
import com.futsalgg.app.domain.auth.usecase.AuthUseCaseImpl
import com.futsalgg.app.domain.team.repository.TeamRepository
import com.futsalgg.app.domain.user.repository.UserRepository
import com.futsalgg.app.domain.user.usecase.SignupUseCaseImpl
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
    ): SignupUseCase {
        return SignupUseCaseImpl(userRepository)
    }

    @Provides
    @Singleton
    fun provideCreateTeamUseCase(
        teamRepository: TeamRepository
    ): CreateTeamUseCase {
        return CreateTeamUseCaseImpl(teamRepository)
    }
} 