package com.jHerscu.clearskies.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "geocoded_city")
data class LocalGeocodedCity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "qualified_name")
    val qualifiedName: String,
    val latitude: Float,
    val longitude: Float,
)
