package com.jHerscu.clearskies.data.model.response

data class DailyAndHourlyWeatherResponse(
    val current: HourlyWeatherResponse,
    val hourly: List<HourlyWeatherResponse>,
    val daily: List<DailyWeatherResponse>
)
