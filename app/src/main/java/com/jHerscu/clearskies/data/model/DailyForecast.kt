package com.jHerscu.clearskies.data.model

import android.graphics.Bitmap

data class DailyForecast(
    // Format w/ SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format()
    val dateInMillis: Long,
    val humidity: Int,
    val minTemp: Float,
    val maxTemp: Float,
    val weatherDescription: String,
    val icon: Bitmap,
) : Forecast
