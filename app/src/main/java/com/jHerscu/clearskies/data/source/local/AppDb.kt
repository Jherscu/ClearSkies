package com.jHerscu.clearskies.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jHerscu.clearskies.data.source.local.entity.GeocodedCity
import com.jHerscu.clearskies.data.source.local.entity.LocalDailyForecast
import com.jHerscu.clearskies.data.source.local.entity.LocalHourlyForecast

@Database(
    entities = [GeocodedCity::class, LocalDailyForecast::class, LocalHourlyForecast::class],
    version = 1,
    exportSchema = false
)
abstract class AppDb : RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDao
    abstract fun getCityDao(): CityDao
}