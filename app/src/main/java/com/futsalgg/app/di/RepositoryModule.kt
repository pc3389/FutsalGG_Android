package com.futsalgg.app.di

import com.futsalgg.app.data.repository.CreateTeamRepositoryImpl
import com.futsalgg.app.data.repository.GoogleLoginRepositoryImpl
import com.futsalgg.app.data.repository.LoginRepositoryImpl
import com.futsalgg.app.data.repository.OkHttpFileUploader
import com.futsalgg.app.data.repository.UserRepositoryImpl
import com.futsalgg.app.domain.repository.CreateTeamRepository
import com.futsalgg.app.domain.repository.FileUploader
import com.futsalgg.app.domain.repository.GoogleLoginRepository
import com.futsalgg.app.domain.repository.LoginRepository
import com.futsalgg.app.domain.repository.UserRepository
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

    @Binds
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository

    @Binds
    abstract fun bindCreateTeamRepository(
        impl: CreateTeamRepositoryImpl
    ): CreateTeamRepository

    @Binds
    abstract fun bindFileUploader(
        impl: OkHttpFileUploader
    ): FileUploader
}