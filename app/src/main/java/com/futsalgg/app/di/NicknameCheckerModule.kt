package com.futsalgg.app.di

import com.futsalgg.app.domain.user.repository.UserRepository
import com.futsalgg.app.presentation.user.util.NicknameChecker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NicknameCheckerModule {
    @Provides
    @Singleton
    fun provideNicknameChecker(userRepository: UserRepository): NicknameChecker {
        return NicknameChecker(userRepository)
    }
}