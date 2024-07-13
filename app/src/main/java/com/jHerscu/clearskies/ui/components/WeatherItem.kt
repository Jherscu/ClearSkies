package com.jHerscu.clearskies.ui.components

import androidx.compose.runtime.Composable

@Composable
fun WeatherItem(data: WeatherItem) {
}

data class WeatherItem(
    val timeMillis: Long,
    val iconUrl: String,
    val lowTemp: Float,
    val highTemp: Float,
    val humidity: Float,
)
