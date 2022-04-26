package com.jHerscu.clearskies.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GeocodedCity::class], version = 1, exportSchema = false)
abstract class CityDB : RoomDatabase() {
    abstract fun getGeocodeDao(): GeocodeDao
}