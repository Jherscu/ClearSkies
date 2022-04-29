package com.jHerscu.clearskies.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherForecast(
    @ColumnInfo(name = "qualified_name")
    val qualifiedName: String,
    @PrimaryKey(autoGenerate = false)
    val date: String,
)