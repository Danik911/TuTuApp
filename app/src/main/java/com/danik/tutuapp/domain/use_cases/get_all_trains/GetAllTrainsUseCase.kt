package com.danik.tutuapp.domain.use_cases.get_all_trains

import androidx.paging.PagingData
import com.danik.tutuapp.data.repository.RepositoryImpl
import com.danik.tutuapp.domain.model.Train
import com.danik.tutuapp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetAllTrainsUseCase(private val repository: Repository) {

    operator fun invoke(): Flow<PagingData<Train>> {
        return repository.getAllTrains()
    }
}