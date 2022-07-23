package com.galaxy_techno.buyer.di

import com.galaxy_techno.buyer.BuildConfig
import com.galaxy_techno.buyer.common.Endpoint
import com.galaxy_techno.buyer.data.remote.ApiDataSource
import com.galaxy_techno.buyer.data.remote.ApiDataSourceImpl
import com.galaxy_techno.buyer.data.remote.ApiService
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideDataSource(apiService: ApiService): ApiDataSource {
        return ApiDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Endpoint.API_HOST)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .addNetworkInterceptor(OkHttpProfilerInterceptor())
                .addInterceptor(OkHttpProfilerInterceptor())
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
        } else {
            OkHttpClient.Builder()
                .build()
        }
    }
}