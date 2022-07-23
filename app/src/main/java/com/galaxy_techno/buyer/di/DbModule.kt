package com.galaxy_techno.buyer.di

import android.content.Context
import androidx.room.Room
import com.galaxy_techno.buyer.common.Constant
import com.galaxy_techno.buyer.data.db.DbDataSource
import com.galaxy_techno.buyer.data.db.DbDataSourceImpl
import com.galaxy_techno.buyer.data.db.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DbModule {
    @Provides
    @Singleton
    fun provideDbDataSource(db: UserDatabase): DbDataSource {
        return DbDataSourceImpl(db)
    }

    @Provides
    @Singleton
    fun provideUserDb(
        @ApplicationContext context: Context
    ): UserDatabase {
        return Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            Constant.DB_NAME
        ).fallbackToDestructiveMigration().build()
    }
}