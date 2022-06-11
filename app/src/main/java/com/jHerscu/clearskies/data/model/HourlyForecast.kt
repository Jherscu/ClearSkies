package com.jHerscu.clearskies.data.model

import android.graphics.Bitmap

data class HourlyForecast(
    val hourInMill: Long,
    val humidity: Int,
    val temp: Float,
    val feelsLikeTemp: Float,
    val weatherDescription: String,
    val icon: Bitmap
) : Forecast
