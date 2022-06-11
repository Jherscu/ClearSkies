package com.jHerscu.clearskies.di

import android.content.Context
import androidx.room.Room
import com.jHerscu.clearskies.data.source.local.AppDb
import com.jHerscu.clearskies.data.source.local.CityDao
import com.jHerscu.clearskies.data.source.local.WeatherDao
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
    fun provideDb(@ApplicationContext context: Context): AppDb {
        return Room.databaseBuilder(
            context,
            AppDb::class.java,
            "app_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideWeatherDao(db: AppDb): WeatherDao = db.getWeatherDao()

    @Singleton
    @Provides
    fun provideCityDao(db: AppDb): CityDao = db.getCityDao()
}