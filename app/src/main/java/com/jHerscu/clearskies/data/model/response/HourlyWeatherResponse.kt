package com.jHerscu.clearskies.data.model.response

import com.squareup.moshi.Json

data class HourlyWeatherResponse(
    @field:Json(name = "dt") val hourInMill: Long,
    val temp: Float,
    @field:Json(name = "feels_like") val feelsLikeTemp: Float,
    val humidity: Int,
    val weather: DescriptionResponse
)
