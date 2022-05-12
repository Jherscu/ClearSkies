package com.jHerscu.clearskies.data.model

data class HourlyForecast(
    val hourInMill: Long,
    val humidity: Int,
    val temp: Float,
    val feelsLikeTemp: Float,
    val weatherDescription: String,
    val iconCode: String
)
