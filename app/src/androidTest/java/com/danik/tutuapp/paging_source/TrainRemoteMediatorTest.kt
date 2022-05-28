package com.danik.tutuapp.paging_source

import androidx.paging.*
import androidx.test.core.app.ApplicationProvider
import com.danik.tutuapp.data.local.TrainDatabase
import com.danik.tutuapp.data.paging_source.TrainRemoteMediator
import com.danik.tutuapp.data.remote.FakeTrainApi
import com.danik.tutuapp.domain.model.Train
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TrainRemoteMediatorTest {

    private lateinit var trainDatabase: TrainDatabase
    private lateinit var trainApi: FakeTrainApi

    @Before
    fun setUp() {
        trainApi = FakeTrainApi()
        trainDatabase = TrainDatabase.createDatabase(
            context = ApplicationProvider.getApplicationContext(),
            useInMemory = true
        )
    }

    @After
    fun cleanUp() {
        trainDatabase.clearAllTables()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun refreshLoadDataMediatorResultSuccessEndOfPageFalse() = runBlocking {

        val trainRemoteMediator = TrainRemoteMediator(
            trainApi = trainApi,
            trainDatabase = trainDatabase
        )

        val state: PagingState<Int, Train> = PagingState(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 3),
            leadingPlaceholderCount = 0
        )

        val result = trainRemoteMediator.load(
            loadType = LoadType.REFRESH,
            state = state
        )
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun refreshLoadDataMediatorResultSuccessEndOfPageTrueWhenThereIsNoData() = runBlocking {

        trainApi.clearPage()

        val trainRemoteMediator = TrainRemoteMediator(
            trainApi = trainApi,
            trainDatabase = trainDatabase
        )

        val state: PagingState<Int, Train> = PagingState(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 3),
            leadingPlaceholderCount = 0
        )

        val result = trainRemoteMediator.load(
            loadType = LoadType.REFRESH,
            state = state
        )
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun refreshLoadDataMediatorResultError() = runBlocking {

        trainApi.throwException()

        val trainRemoteMediator = TrainRemoteMediator(
            trainApi = trainApi,
            trainDatabase = trainDatabase
        )

        val state: PagingState<Int, Train> = PagingState(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 3),
            leadingPlaceholderCount = 0
        )

        val result = trainRemoteMediator.load(
            loadType = LoadType.REFRESH,
            state = state
        )
        assertTrue(result is RemoteMediator.MediatorResult.Error)

    }
}