package com.jHerscu.clearskies.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "geocoded_city")
data class LocalGeocodedCity(
    @NonNull
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "qualified_name")
    val qualifiedName: String,
    @NonNull
    val latitude: Float,
    @NonNull
    val longitude: Float
)