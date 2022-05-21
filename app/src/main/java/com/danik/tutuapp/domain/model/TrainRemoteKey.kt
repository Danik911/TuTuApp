package com.danik.tutuapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.danik.tutuapp.util.Constants.TRAIN_REMOTE_KEY_TABLE

@Entity(tableName = TRAIN_REMOTE_KEY_TABLE)
data class TrainRemoteKey(
    @PrimaryKey
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdate: Long?
)