package com.jHerscu.clearskies.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "hourly_forecast",
    primaryKeys = ["qualified_name", "time_in_millis"],
)
data class LocalHourlyForecast(
    @ColumnInfo(name = "qualified_name")
    val qualifiedName: String,
    @ColumnInfo(name = "time_in_millis")
    val timeInMillis: Long,
    val humidity: Int,
    val temp: Float,
    @ColumnInfo(name = "feels_like_temp")
    val feelsLikeTemp: Float,
    @ColumnInfo(name = "weather_description")
    val weatherDescription: String,
    @ColumnInfo(name = "icon_code")
    val iconCode: String,
)
