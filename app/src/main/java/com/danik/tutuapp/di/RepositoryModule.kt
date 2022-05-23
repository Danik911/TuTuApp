package com.danik.tutuapp.di

import com.danik.tutuapp.data.repository.Repository
import com.danik.tutuapp.domain.use_cases.UseCases
import com.danik.tutuapp.domain.use_cases.get_all_trains.GetAllTrainsUseCase
import com.danik.tutuapp.domain.use_cases.get_selected_train.GetSelectedTrainUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUseCases(repository: Repository): UseCases {
        return UseCases(
            getAllTrainsUseCase = GetAllTrainsUseCase(repository = repository),
            getSelectedTrainUseCase = GetSelectedTrainUseCase(repository = repository)
        )
    }
}