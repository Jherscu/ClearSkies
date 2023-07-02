package com.jHerscu.clearskies.data.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CityResponse(
    val latitude: Float,
    val longitude: Float,
    val stateCode: String,
    val city: String
)
