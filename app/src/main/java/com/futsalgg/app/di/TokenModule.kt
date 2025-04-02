package com.futsalgg.app.di

import android.content.Context
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.framework.auth.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TokenModule {

    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): ITokenManager {
        return TokenManager(context)
    }
}