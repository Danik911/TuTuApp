package com.danik.tutuapp.presentation.screens.main

import androidx.lifecycle.ViewModel
import com.danik.tutuapp.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(useCases: UseCases): ViewModel() {

    val allTrains = useCases.getAllTrainsUseCase()


}