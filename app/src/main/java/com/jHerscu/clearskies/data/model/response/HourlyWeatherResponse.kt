package com.jHerscu.clearskies.data.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HourlyWeatherResponse(
    // TODO(jherscu): convert all responses to millis, received in seconds!
    @Json(name = "dt")
    val hourInMillis: Long,
    val temp: Float,
    @Json(name = "feels_like")
    val feelsLikeTemp: Float,
    val humidity: Int,
    val weather: DescriptionResponse,
)
