package com.danik.tutuapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val success: Boolean,
    val message: String? = null,
    val prevPage: Int? = null,
    val nextPage: Int? = null,
    val trains: List<Train> = emptyList(),
    val lastUpdate: Long? = null
)