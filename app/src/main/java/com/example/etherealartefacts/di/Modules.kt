package com.example.etherealartefacts.di

import com.example.etherealartefacts.networking.API
import com.example.etherealartefacts.networking.APIClient
import com.example.etherealartefacts.networking.JWTInterceptor
import com.example.etherealartefacts.networking.JWTTokenProvider
import com.example.etherealartefacts.repository.DefaultRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module()
@InstallIn(SingletonComponent::class)
object NetworkingModules {
    @Provides
    @Singleton
    fun providesJWTTokenProvider(): JWTTokenProvider {
        return JWTTokenProvider()
    }

    @Provides
    @Singleton
    fun providesJWTInterceptor(jwtTokenProvider: JWTTokenProvider): JWTInterceptor {
        return JWTInterceptor(jwtTokenProvider)
    }

    @Provides
    @Singleton
    fun providesAPI(providesJWTInterceptor: JWTInterceptor): API {
        return APIClient(providesJWTInterceptor).defaultService
    }

    @Provides
    @Singleton
    fun providesRepository(apiService: API) = DefaultRepository(apiService)
}