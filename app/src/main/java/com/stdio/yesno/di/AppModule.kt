package com.stdio.yesno.di

import com.stdio.yesno.data.MainRepository
import com.stdio.yesno.data.MainService
import com.stdio.yesno.data.OAuthInterceptor
import com.stdio.yesno.data.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesBaseUrl(): String = "https://mb-ci.cifra.app/api/v1/"

    @Provides
    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun getHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .callTimeout(1, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .addInterceptor(OAuthInterceptor())
            .addInterceptor(httpLoggingInterceptor)
    }

    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL: String, httpBuilder: OkHttpClient.Builder): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(httpBuilder.build())
            .build()

    @Provides
    @Singleton
    fun provideMainService(retrofit: Retrofit): MainService =
        retrofit.create(MainService::class.java)

    @Provides
    @Singleton
    fun provideRemoteDataSource(mainService: MainService) =
        RemoteDataSource(mainService)

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: RemoteDataSource) =
        MainRepository(remoteDataSource)
}