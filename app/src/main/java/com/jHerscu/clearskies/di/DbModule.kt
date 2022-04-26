package com.jHerscu.clearskies.di

import android.content.Context
import androidx.room.Room
import com.jHerscu.clearskies.data.source.local.CityDB
import com.jHerscu.clearskies.data.source.local.WeatherDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideCityDB(@ApplicationContext context: Context): CityDB {
        return Room.databaseBuilder(
            context,
            CityDB::class.java,
            "city_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideGeocodeDao(db: CityDB) = db.getGeocodeDao()

    @Singleton
    @Provides
    fun provideWeatherDB(@ApplicationContext context: Context): WeatherDB {
        return Room.databaseBuilder(
            context,
            WeatherDB::class.java,
            "weather_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideWeatherDao(db: WeatherDB) = db.getWeatherDao()
}