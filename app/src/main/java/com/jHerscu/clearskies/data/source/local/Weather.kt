package com.jHerscu.clearskies.data.source.local

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Weather(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @NonNull
    val latitude: Float,
    @NonNull
    val longitude: Float
    )