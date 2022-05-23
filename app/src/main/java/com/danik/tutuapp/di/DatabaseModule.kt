package com.danik.tutuapp.di

import android.content.Context
import androidx.room.Room
import com.danik.tutuapp.data.local.TrainDatabase
import com.danik.tutuapp.data.repository.LocalDataSourceImpl
import com.danik.tutuapp.domain.repository.LocalDataSource
import com.danik.tutuapp.util.Constants.TRAIN_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TrainDatabase {
        return Room.databaseBuilder(
            context,
            TrainDatabase::class.java,
            TRAIN_DATABASE
        ).build()
    }


    @Provides
    @Singleton
    fun provideLocalDataSource(trainDatabase: TrainDatabase): LocalDataSource {
        return LocalDataSourceImpl(database = trainDatabase)
    }

}