package com.jHerscu.clearskies.data.source.local

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "geocoded_city")
data class GeocodedCity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @NonNull
    val name: String,
    @NonNull
    val latitude: Float,
    @NonNull
    val longitude: Float
)