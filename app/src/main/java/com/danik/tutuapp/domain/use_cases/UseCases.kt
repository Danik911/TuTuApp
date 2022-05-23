package com.danik.tutuapp.domain.use_cases

import com.danik.tutuapp.domain.use_cases.get_all_trains.GetAllTrainsUseCase
import com.danik.tutuapp.domain.use_cases.get_selected_train.GetSelectedTrainUseCase

data class UseCases(
     val getAllTrainsUseCase: GetAllTrainsUseCase,
     val getSelectedTrainUseCase: GetSelectedTrainUseCase
)
