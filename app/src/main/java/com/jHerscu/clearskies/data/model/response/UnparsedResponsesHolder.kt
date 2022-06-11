package com.jHerscu.clearskies.data.model.response

data class UnparsedResponsesHolder(
    val dailyAndHourlyWeatherResponse: DailyAndHourlyWeatherResponse,
    val yesterdaysWeatherResponse: YesterdaysWeatherResponse
)
