package com.danik.tutuapp.domain.repository

import androidx.paging.PagingData
import com.danik.tutuapp.domain.model.Train
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    fun getAllTrains(): Flow<PagingData<Train>>

}