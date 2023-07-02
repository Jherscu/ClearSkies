package com.jHerscu.clearskies.data.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UnparsedResponsesHolder(
    val dailyAndHourlyWeatherResponse: DailyAndHourlyWeatherResponse,
    val yesterdaysWeatherResponse: YesterdaysWeatherResponse
)
