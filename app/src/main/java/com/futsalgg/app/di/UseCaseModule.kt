package com.futsalgg.app.di

import com.futsalgg.app.core.token.ITokenManager
import com.futsalgg.app.domain.repository.CreateTeamRepository
import com.futsalgg.app.domain.repository.GoogleLoginRepository
import com.futsalgg.app.domain.repository.LoginRepository
import com.futsalgg.app.domain.repository.UserRepository
import com.futsalgg.app.domain.usecase.CreateTeamUseCase
import com.futsalgg.app.domain.usecase.LoginUseCase
import com.futsalgg.app.domain.usecase.SignupUseCase
import com.futsalgg.app.domain.usecase.impl.CreateTeamUseCaseImpl
import com.futsalgg.app.domain.usecase.impl.LoginUseCaseImpl
import com.futsalgg.app.domain.usecase.impl.SignupUseCaseImpl
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
    fun provideLoginUseCase(
        loginRepository: LoginRepository,
        googleLoginRepository: GoogleLoginRepository,
        tokenManager: ITokenManager
    ): LoginUseCase {
        return LoginUseCaseImpl(loginRepository, googleLoginRepository, tokenManager)
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
        createTeamRepository: CreateTeamRepository
    ): CreateTeamUseCase {
        return CreateTeamUseCaseImpl(createTeamRepository)
    }
} 