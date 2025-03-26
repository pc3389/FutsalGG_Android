package com.futsalgg.app.data.di

import com.futsalgg.app.data.repository.GoogleLoginRepositoryImpl
import com.futsalgg.app.data.repository.LoginRepositoryImpl
import com.futsalgg.app.domain.repository.GoogleLoginRepository
import com.futsalgg.app.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindGoogleLoginRepository(
        impl: GoogleLoginRepositoryImpl
    ): GoogleLoginRepository

    @Binds
    abstract fun bindLoginRepository(
        impl: LoginRepositoryImpl
    ): LoginRepository
}