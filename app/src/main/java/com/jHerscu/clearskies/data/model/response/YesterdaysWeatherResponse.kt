package com.jHerscu.clearskies.data.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class YesterdaysWeatherResponse(
    val current: PreviousDailyWeatherResponse,
    val hourly: List<HourlyWeatherResponse>
)
