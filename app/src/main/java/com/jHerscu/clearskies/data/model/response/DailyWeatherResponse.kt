package com.jHerscu.clearskies.data.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DailyWeatherResponse(
    @Json(name = "dt")
    val dateInMillis: Long,
    val temp: DailyTempResponse,
    val humidity: Int,
    val weather: DescriptionResponse,
)
