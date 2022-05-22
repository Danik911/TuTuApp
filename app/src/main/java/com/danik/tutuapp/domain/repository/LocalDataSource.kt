package com.danik.tutuapp.domain.repository

import com.danik.tutuapp.domain.model.Train

interface LocalDataSource {

    suspend fun getSelectedTrain(trainId: Int): Train
}