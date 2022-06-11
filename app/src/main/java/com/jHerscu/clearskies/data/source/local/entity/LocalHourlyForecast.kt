package com.jHerscu.clearskies.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "hourly_forecast",
    primaryKeys = ["qualified_name", "time_in_mill"]
)
data class LocalHourlyForecast(
    @NonNull
    @ColumnInfo(name = "qualified_name")
    val qualifiedName: String,
    @NonNull
    @ColumnInfo(name = "time_in_mill")
    val timeInMill: Long,
    @NonNull
    val humidity: Int,
    @NonNull
    val temp: Float,
    @NonNull
    @ColumnInfo(name = "feels_like_temp")
    val feelsLikeTemp: Float,
    @NonNull
    @ColumnInfo(name = "weather_description")
    val weatherDescription: String,
    @NonNull
    @ColumnInfo(name = "icon_code")
    val iconCode: String
)
