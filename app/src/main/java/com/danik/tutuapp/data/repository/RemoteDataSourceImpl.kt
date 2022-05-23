package com.danik.tutuapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.danik.tutuapp.data.local.TrainDatabase
import com.danik.tutuapp.data.local.dao.TrainDao
import com.danik.tutuapp.data.remote.TrainApi
import com.danik.tutuapp.domain.model.ApiResponse
import com.danik.tutuapp.domain.model.Train
import com.danik.tutuapp.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow

class RemoteDataSourceImpl(
    private val trainApi: TrainApi,
    private val trainDatabase: TrainDatabase
): RemoteDataSource {

    val trainDao = trainDatabase.trainDao()

    override fun getAllTrains(): Flow<PagingData<Train>> {
        val pagerFactory = {trainDao.getAllTrains()}

        return Pager(
            config = PagingConfig(pageSize = 3),
            pagingSourceFactory = pagerFactory,
            remoteMediator = TraR
        )
    }


}