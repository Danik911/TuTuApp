package com.danik.tutuapp.domain.use_cases.get_all_trains

import android.util.Log
import androidx.paging.PagingData
import com.danik.tutuapp.data.repository.Repository
import com.danik.tutuapp.domain.model.Train
import kotlinx.coroutines.flow.Flow

class GetAllTrainsUseCase(private val repository: Repository) {

    operator fun invoke(): Flow<PagingData<Train>> {
        Log.d("GetAllTrainsUseCase", "GetAllTrainsUseCase Has been called")
        return repository.getAllTrains()
    }
}