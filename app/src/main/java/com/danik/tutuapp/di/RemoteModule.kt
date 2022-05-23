package com.danik.tutuapp.di

import com.danik.tutuapp.data.local.TrainDatabase
import com.danik.tutuapp.data.remote.TrainApi
import com.danik.tutuapp.data.repository.RemoteDataSourceImpl
import com.danik.tutuapp.domain.repository.RemoteDataSource
import com.danik.tutuapp.util.Constants.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import okhttp3.MediaType.Companion.toMediaType
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@OptIn(ExperimentalSerializationApi::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .callTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory(contentType = contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideTrainApi(retrofit: Retrofit): TrainApi {
        return retrofit.create(TrainApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        trainApi: TrainApi,
        trainDatabase: TrainDatabase
    ): RemoteDataSource {
        return RemoteDataSourceImpl(
            trainApi = trainApi,
            trainDatabase = trainDatabase
        )
    }
}