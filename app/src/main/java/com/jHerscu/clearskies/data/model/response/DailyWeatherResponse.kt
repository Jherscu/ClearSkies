package com.jHerscu.clearskies.data.model.response

import com.squareup.moshi.Json

data class DailyWeatherResponse(
    @field:Json(name = "dt") val dateInMillis: Long,
    val temp: DailyTempResponse,
    val humidity: Int,
    val weather: DescriptionResponse
)
