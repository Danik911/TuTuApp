package com.danik.tutuapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danik.tutuapp.domain.model.TrainRemoteKey

@Dao
interface TrainRemoteKeyDao {

    @Query("SELECT * FROM train_remote_key_table WHERE id=:trainId")
    suspend fun getRemoteKey(trainId: Int): TrainRemoteKey?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRemoteKeys(trainRemoteKeys: List<TrainRemoteKey>)

    @Query("DELETE FROM train_remote_key_table")
    suspend fun deleteAllRemoteKeys()
}