package com.futsalgg.app.di

import com.futsalgg.app.remote.api.auth.AuthApi
import com.futsalgg.app.remote.api.match.MatchApi
import com.futsalgg.app.remote.api.team.TeamApi
import com.futsalgg.app.remote.api.team.TeamMemberApi
import com.futsalgg.app.remote.api.user.UserApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    fun provideBaseUrl(): String {
        return "https://futsal-gg.p-e.kr/"
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        .create()

    @Provides
    @Singleton
    fun provideRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)  // OkHttpClient 설정
            .addConverterFactory(GsonConverterFactory.create(gson))  // Gson 설정
            .build()

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTeamApi(retrofit: Retrofit): TeamApi {
        return retrofit.create(TeamApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTeamMemberApi(retrofit: Retrofit): TeamMemberApi {
        return retrofit.create(TeamMemberApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMatchApi(retrofit: Retrofit): MatchApi {
        return retrofit.create(MatchApi::class.java)
    }
}