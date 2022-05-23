package com.danik.tutuapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.danik.tutuapp.data.local.TrainDatabase
import com.danik.tutuapp.data.paging_source.TrainRemoteMediator
import com.danik.tutuapp.data.remote.TrainApi
import com.danik.tutuapp.domain.model.Train
import com.danik.tutuapp.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagingApi::class)
class RemoteDataSourceImpl(
    private val trainApi: TrainApi,
    private val trainDatabase: TrainDatabase
) : RemoteDataSource {

    private val trainDao = trainDatabase.trainDao()


    override fun getAllTrains(): Flow<PagingData<Train>> {
        val pagerFactory = { trainDao.getAllTrains() }

        return Pager(
            config = PagingConfig(pageSize = 3),
            pagingSourceFactory = pagerFactory,
            remoteMediator = TrainRemoteMediator(
                trainApi = trainApi,
                trainDatabase = trainDatabase
            )
        ).flow
    }


}