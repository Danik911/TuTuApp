package com.danik.tutuapp.domain.use_cases.get_all_tains

import com.danik.tutuapp.data.repository.FakeRepository
import com.danik.tutuapp.domain.use_cases.get_all_trains.GetAllTrainsUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetAllTrainsUseCaseTest {

    private lateinit var repository: FakeRepository
    private lateinit var getAllTrainsUseCase: GetAllTrainsUseCase

    @Before
    fun setUp() {
        repository = FakeRepository()
        getAllTrainsUseCase = GetAllTrainsUseCase(repository)
    }

    @Test
    fun `get all trains from a page, assert result is not empty`() {
        runBlocking {
            val trains = getAllTrainsUseCase.invoke().count()
            assertThat(trains).isGreaterThan(0)
        }
    }
}