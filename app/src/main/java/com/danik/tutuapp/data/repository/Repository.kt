package com.danik.tutuapp.data.repository

import androidx.paging.PagingData
import com.danik.tutuapp.domain.model.Train
import com.danik.tutuapp.domain.repository.LocalDataSource
import com.danik.tutuapp.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun getSelectedTrain(trainId: Int): Train {
        return localDataSource.getSelectedTrain(trainId = trainId)
    }

    fun getAllTrains(): Flow<PagingData<Train>> {
        return remoteDataSource.getAllTrains()
    }
}