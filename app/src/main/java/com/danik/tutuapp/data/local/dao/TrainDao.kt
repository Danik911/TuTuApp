package com.danik.tutuapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danik.tutuapp.domain.model.Train

@Dao
interface TrainDao {

    @Query("SELECT * FROM train_database_table ORDER BY id ASC")
    fun getAllTrains(): PagingSource<Int, Train>

    @Query("SELECT * FROM train_database_table WHERE id=:trainId")
    fun searchTrain(trainId: Int): Train

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrains(trains: List<Train>)

    @Query("DELETE FROM train_database_table")
    fun deleteAllTrains()
}