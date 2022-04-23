package com.jHerscu.clearskies.di

import com.jHerscu.clearskies.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Named("weather_key")
    fun provideWeatherApiKey(): String {
        return BuildConfig.OPEN_WEATHER_KEY
    }

    @Provides
    @Named("geocode_key")
    fun provideGeocodeApiKey(): String {
        return BuildConfig.RADAR_GEOCODE_KEY
    }

    @Singleton
    @Provides
    @Named("weather_url")
    fun provideBaseWeatherUrl(): String {
        return "https://api.openweathermap.org/data/2.5/onecall"
    }

    @Singleton
    @Provides
    @Named("geocode_url")
    fun provideBaseGeocodeUrl(): String {
        return "https://api.radar.io/v1/search/autocomplete"
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    @Named("weather_retrofit")
    fun provideWeatherRetrofit(moshi: Moshi, @Named("weather_url") url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    @Named("geocode_retrofit")
    fun provideGeocodeRetrofit(moshi: Moshi, @Named("geocode_url") url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

}