package com.futsalgg.app.di

import com.futsalgg.app.data.auth.repository.AuthRepositoryImpl
import com.futsalgg.app.data.file.repository.OkHttpFileUploaderImpl
import com.futsalgg.app.data.match.repository.MatchParticipantRepositoryImpl
import com.futsalgg.app.data.match.repository.MatchRepositoryImpl
import com.futsalgg.app.data.team.repository.TeamRepositoryImpl
import com.futsalgg.app.data.team.repository.TeamMemberRepositoryImpl
import com.futsalgg.app.data.user.repository.UserRepositoryImpl
import com.futsalgg.app.domain.auth.repository.AuthRepository
import com.futsalgg.app.domain.file.repository.OkHttpFileUploader
import com.futsalgg.app.domain.match.repository.MatchParticipantRepository
import com.futsalgg.app.domain.match.repository.MatchRepository
import com.futsalgg.app.domain.team.repository.TeamRepository
import com.futsalgg.app.domain.team.repository.TeamMemberRepository
import com.futsalgg.app.domain.user.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindLoginRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository

    @Binds
    abstract fun bindTeamRepository(
        impl: TeamRepositoryImpl
    ): TeamRepository

    @Binds
    abstract fun bindTeamMemberRepository(
        impl: TeamMemberRepositoryImpl
    ): TeamMemberRepository

    @Binds
    abstract fun bindFileUploader(
        impl: OkHttpFileUploaderImpl
    ): OkHttpFileUploader

    @Binds
    abstract fun bindMatchRepository(
        matchRepositoryImpl: MatchRepositoryImpl
    ): MatchRepository

    @Binds
    abstract fun bindMatchParticipantReporisoty(
        matchParticipantRepositoryImpl: MatchParticipantRepositoryImpl
    ): MatchParticipantRepository
}