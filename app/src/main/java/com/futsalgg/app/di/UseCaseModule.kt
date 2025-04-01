package com.futsalgg.app.di

import com.futsalgg.app.domain.usecase.LoginUseCase
import com.futsalgg.app.domain.usecase.LoginUseCaseImpl
import com.futsalgg.app.domain.usecase.SignupUseCase
import com.futsalgg.app.domain.usecase.SignupUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    @Singleton
    abstract fun bindLoginUseCase(
        impl: LoginUseCaseImpl
    ): LoginUseCase

    @Binds
    @Singleton
    abstract fun bindSignupUseCase(
        impl: SignupUseCaseImpl
    ): SignupUseCase
} 