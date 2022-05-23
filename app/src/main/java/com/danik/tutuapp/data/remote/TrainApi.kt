package com.danik.tutuapp.data.remote

import com.danik.tutuapp.domain.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TrainApi {

    @GET("/tutu/trains")
    suspend fun getAllTrains(
        @Query("page") page: Int = 1
    ): ApiResponse


}