package com.danik.tutuapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.danik.tutuapp.data.local.dao.TrainDao
import com.danik.tutuapp.data.local.dao.TrainRemoteKeyDao
import com.danik.tutuapp.domain.model.Train
import com.danik.tutuapp.domain.model.TrainRemoteKey

@Database(entities = [Train::class, TrainRemoteKey::class], version = 1, exportSchema = false)
@TypeConverters(DatabaseConverter::class)
abstract class TrainDatabase : RoomDatabase() {

    companion object {
        fun createDatabase(context: Context, useInMemory: Boolean): TrainDatabase {
            val database = if (useInMemory) {
                Room.inMemoryDatabaseBuilder(
                    context,
                    TrainDatabase::class.java
                )
            } else {
                Room.databaseBuilder(
                    context,
                    TrainDatabase::class.java,
                    "testDatabase"
                )
            }
            return database
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun heroDao(): TrainDao
    abstract fun heroRemoteKeyDao(): TrainRemoteKeyDao
}