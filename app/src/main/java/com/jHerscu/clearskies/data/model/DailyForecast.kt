package com.jHerscu.clearskies.data.model

data class DailyForecast(
    val dateInMill: Long, // Format w/ SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format()
    val humidity: Int,
    val minTemp: Float,
    val maxTemp: Float,
    val weatherDescription: String,
    val iconCode: String
)
