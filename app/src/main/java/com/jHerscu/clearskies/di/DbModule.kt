package com.jHerscu.clearskies.di

import android.content.Context
import androidx.room.Room
import com.jHerscu.clearskies.data.source.local.AppDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
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
    @Named("weather_dao")
    fun provideWeatherDao(db: AppDb) = db.getWeatherDao()

    @Singleton
    @Provides
    @Named("city_dao")
    fun provideCityDao(db: AppDb) = db.getCityDao()
}