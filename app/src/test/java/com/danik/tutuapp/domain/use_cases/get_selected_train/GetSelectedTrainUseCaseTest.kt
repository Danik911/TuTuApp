package com.danik.tutuapp.domain.use_cases.get_selected_train

import com.danik.tutuapp.data.repository.FakeRepository
import com.danik.tutuapp.domain.use_cases.get_all_trains.GetAllTrainsUseCase
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetSelectedTrainUseCaseTest {

    private lateinit var repository: FakeRepository
    private lateinit var getAllTrainsUseCase: GetSelectedTrainUseCase

    @Before
    fun setUp() {
        repository = FakeRepository()
        getAllTrainsUseCase = GetSelectedTrainUseCase(repository)
    }

    @Test
    fun `select a train by Id, assert result is train with this id`() {
        runBlocking {
            val selectedTrain = getAllTrainsUseCase.invoke(trainId = 2)
            assertThat(selectedTrain.id).isEqualTo(2)
        }
    }

}

