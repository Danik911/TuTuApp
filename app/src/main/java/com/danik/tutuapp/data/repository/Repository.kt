package com.danik.tutuapp.data.repository

import com.danik.tutuapp.domain.model.Train
import com.danik.tutuapp.domain.repository.LocalDataSource
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource
) {
    suspend fun getSelectedTrain(trainId: Int): Train{
        return localDataSource.getSelectedTrain(trainId = trainId)
    }
}