package com.jHerscu.clearskies.data.model.response

import com.squareup.moshi.Json

data class CityResponse(
    @field:Json(name = "latitude") val lat: Float,
    @field:Json(name = "longitude") val lon: Float,
    val stateCode: String,
    val city: String
)
