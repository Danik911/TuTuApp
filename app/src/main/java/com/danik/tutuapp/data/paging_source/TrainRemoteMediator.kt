package com.danik.tutuapp.data.paging_source

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.danik.tutuapp.data.local.TrainDatabase
import com.danik.tutuapp.data.remote.TrainApi
import com.danik.tutuapp.domain.model.Train
import com.danik.tutuapp.domain.model.TrainRemoteKey
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@OptIn(ExperimentalPagingApi::class)
class TrainRemoteMediator @Inject constructor(
    private val trainApi: TrainApi,
    private val trainDatabase: TrainDatabase
) : RemoteMediator<Int, Train>() {

    private val trainDao = trainDatabase.trainDao()
    private val trainRemoteKeyDao = trainDatabase.trainRemoteKeyDao()

    override suspend fun initialize(): InitializeAction {
        val maxTimeout = 1440
        val currentTime = System.currentTimeMillis()
        val lastUpdate = trainRemoteKeyDao.getRemoteKey(1)?.lastUpdate ?: 0L
        val timePassed = (currentTime - lastUpdate) / 1000 / 60
        return if (timePassed <= maxTimeout) {
            Log.d("TrainRemoteMediator", parseMillis(currentTime))
            Log.d("TrainRemoteMediator", parseMillis(lastUpdate))
            Log.d("TrainRemoteMediator", "Up to date")
            InitializeAction.SKIP_INITIAL_REFRESH

        } else {
            Log.d("TrainRemoteMediator", parseMillis(currentTime))
            Log.d("TrainRemoteMediator", parseMillis(lastUpdate))
            Log.d("TrainRemoteMediator", "Refresh")
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Train>
    ): MediatorResult {

        return try {

            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeysClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                    Log.d("TrainRemoteMediator", "Refresh")
                }
                LoadType.PREPEND -> {
                    Log.d("TrainRemoteMediator", "Prepend")
                    val remoteKeys = getRemoteKeysForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage


                }
                LoadType.APPEND -> {
                    Log.d("TrainRemoteMediator", "Append")
                    val remoteKey = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKey?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKey != null
                    )
                    nextPage

                }
            }

            val response = trainApi.getAllTrains(page = page)
            if (response.trains.isNotEmpty()) {

                trainDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        trainDao.deleteAllTrains()
                        trainRemoteKeyDao.deleteAllRemoteKeys()
                    }
                    val prevPage = response.prevPage
                    val nextPage = response.nextPage
                    val lastUpdate = response.lastUpdate

                    val remoteKeys = response.trains.map { train ->
                        TrainRemoteKey(
                            id = train.id,
                            prevPage = prevPage,
                            nextPage = nextPage,
                            lastUpdate = lastUpdate
                        )
                    }
                    trainRemoteKeyDao.addRemoteKeys(trainRemoteKeys = remoteKeys)
                    trainDao.insertTrains(trains = response.trains)

                }

            }
            MediatorResult.Success(
                endOfPaginationReached = response.nextPage == null
            )

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }


    private suspend fun getRemoteKeysClosestToCurrentPosition(state: PagingState<Int, Train>): TrainRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                trainRemoteKeyDao.getRemoteKey(id)
            }
        }
    }

    private suspend fun getRemoteKeysForFirstItem(state: PagingState<Int, Train>): TrainRemoteKey? {


        val firstPage = state.pages.firstOrNull()
        val firstRemoteKey =
            if (firstPage?.data?.isNotEmpty() == true) {
                firstPage.data.firstOrNull()?.let { train ->
                    trainRemoteKeyDao.getRemoteKey(trainId = train.id)
                }
            } else null
        return firstRemoteKey
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Train>): TrainRemoteKey? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { train ->
            trainRemoteKeyDao.getRemoteKey(trainId = train.id)
        }
    }

    private fun parseMillis(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.ROOT)
        return format.format(date)
    }
}
