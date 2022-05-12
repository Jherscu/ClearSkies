package com.jHerscu.clearskies.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalDailyForecast(
    @NonNull
    @ColumnInfo(name = "qualified_name")
    val qualifiedName: String,
    @NonNull
    @PrimaryKey(autoGenerate = false)
    val dateInMill: Long,
    @NonNull
    val humidity: Int,
    @NonNull
    val minTemp: Float,
    @NonNull
    val maxTemp: Float,
    @NonNull
    val weatherDescription: String,
    @NonNull
    val iconCode: String
)