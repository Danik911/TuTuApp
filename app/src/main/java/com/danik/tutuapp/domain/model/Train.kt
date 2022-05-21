package com.danik.tutuapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.danik.tutuapp.util.Constants.TRAIN_DATABASE_TABLE
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = TRAIN_DATABASE_TABLE)
data class Train(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val model: String,
    val image: String,
    val about: String,
)