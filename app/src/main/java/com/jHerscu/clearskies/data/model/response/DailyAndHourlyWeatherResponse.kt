package com.jHerscu.clearskies.data.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DailyAndHourlyWeatherResponse(
    val current: HourlyWeatherResponse,
    val hourly: List<HourlyWeatherResponse>,
    val daily: List<DailyWeatherResponse>
)
