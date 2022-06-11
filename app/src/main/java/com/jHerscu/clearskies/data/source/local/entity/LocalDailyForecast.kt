package com.jHerscu.clearskies.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "daily_forecast",
    primaryKeys = ["qualified_name", "time_in_mill"]
)
data class LocalDailyForecast(
    @NonNull
    @ColumnInfo(name = "qualified_name")
    val qualifiedName: String,
    @NonNull
    @ColumnInfo(name = "time_in_mill")
    val timeInMill: Long,
    @NonNull
    val humidity: Int,
    @NonNull
    val minTemp: Float,
    @NonNull
    val maxTemp: Float,
    @NonNull
    @ColumnInfo(name = "weather_description")
    val weatherDescription: String,
    @NonNull
    @ColumnInfo(name = "icon_code")
    val iconCode: String
)