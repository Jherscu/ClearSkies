package com.jHerscu.clearskies.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jHerscu.clearskies.data.source.local.entity.LocalDailyForecast
import com.jHerscu.clearskies.data.source.local.entity.LocalGeocodedCity
import com.jHerscu.clearskies.data.source.local.entity.LocalHourlyForecast
import com.jHerscu.clearskies.data.source.local.entity.LocalIcon
import com.jHerscu.clearskies.utils.BitmapTypeConverter

@Database(
    entities = [LocalGeocodedCity::class, LocalDailyForecast::class, LocalHourlyForecast::class, LocalIcon::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(BitmapTypeConverter::class)
abstract class AppDb : RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDao
    abstract fun getCityDao(): CityDao
}