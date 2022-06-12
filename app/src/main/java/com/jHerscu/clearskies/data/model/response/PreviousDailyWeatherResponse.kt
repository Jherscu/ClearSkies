package com.jHerscu.clearskies.data.model.response

import com.squareup.moshi.Json

data class PreviousDailyWeatherResponse(
    @field:Json(name = "dt") val dateInMillis: Long,
    val temp: Float,
    val humidity: Int,
    val weather: DescriptionResponse
)
