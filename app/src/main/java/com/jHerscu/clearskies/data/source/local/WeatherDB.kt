package com.jHerscu.clearskies.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Weather::class], version = 1, exportSchema = false)
abstract class WeatherDB : RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDao
}