package com.danik.tutuapp.domain.use_cases.get_selected_train

import com.danik.tutuapp.data.repository.RepositoryImpl
import com.danik.tutuapp.domain.model.Train
import com.danik.tutuapp.domain.repository.Repository

class GetSelectedTrainUseCase (private val repository: Repository){

    suspend operator fun invoke(trainId: Int): Train{
        return repository.getSelectedTrain(trainId = trainId)
    }
}