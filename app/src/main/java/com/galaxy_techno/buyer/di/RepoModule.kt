package com.galaxy_techno.buyer.di

import com.galaxy_techno.buyer.data.db.DbDataSource
import com.galaxy_techno.buyer.data.ds.DsDataSource
import com.galaxy_techno.buyer.data.remote.ApiDataSource
import com.galaxy_techno.buyer.data.remote.ApiService
import com.galaxy_techno.buyer.domain.AppRepository
import com.galaxy_techno.buyer.domain.AppRepositoryImpl
import com.galaxy_techno.buyer.domain.UserRepository
import com.galaxy_techno.buyer.domain.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepoModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        apiDataSource: ApiDataSource,
        dbDataSource: DbDataSource,
        dsDataSource: DsDataSource,
        @Qualifier.Io io: CoroutineDispatcher
    ): UserRepository {
        return UserRepositoryImpl(
            apiDataSource,
            dbDataSource,
            dsDataSource,
            io
        )
    }

    @Provides
    @Singleton
    fun provideAppRepository(
        apiDataSource: ApiDataSource,
        dbDataSource: DbDataSource,
        dsDataSource: DsDataSource,
        apiApiSource: ApiService,
        @Qualifier.Io io: CoroutineDispatcher
    ): AppRepository {
        return AppRepositoryImpl(
            apiDataSource,
            dbDataSource,
            dsDataSource,
            apiApiSource,
            io
        )
    }
}