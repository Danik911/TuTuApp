package com.danik.tutuapp.data.repository

import com.danik.tutuapp.data.local.TrainDatabase
import com.danik.tutuapp.domain.model.Train
import com.danik.tutuapp.domain.repository.LocalDataSource

class LocalDataSourceImpl(database: TrainDatabase) : LocalDataSource {

    private val trainDao = database.trainDao()

    override suspend fun getSelectedTrain(trainId: Int): Train {
        return trainDao.searchTrain(trainId = trainId)
    }


}