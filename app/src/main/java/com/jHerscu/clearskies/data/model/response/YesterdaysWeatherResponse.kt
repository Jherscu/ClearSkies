package com.jHerscu.clearskies.data.model.response

data class YesterdaysWeatherResponse(
    val current: PreviousDailyWeatherResponse,
    val hourly: List<HourlyWeatherResponse>
)
