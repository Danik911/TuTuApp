package com.danik.tutuapp.domain.repository

import androidx.paging.PagingData
import com.danik.tutuapp.domain.model.Train
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getSelectedTrain(trainId: Int): Train
    fun getAllTrains(): Flow<PagingData<Train>>
}