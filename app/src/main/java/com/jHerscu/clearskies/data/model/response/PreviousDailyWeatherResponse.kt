package com.jHerscu.clearskies.data.model.response

import com.squareup.moshi.Json

data class PreviousDailyWeatherResponse(
    @field:Json(name = "dt") val dateInMill: Long,
    val temp: Float,
    val humidity: Int,
    val weather: DescriptionResponse
)
