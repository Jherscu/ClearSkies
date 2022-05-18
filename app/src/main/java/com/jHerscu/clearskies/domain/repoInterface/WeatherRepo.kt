package com.jHerscu.clearskies.domain.repoInterface

import com.jHerscu.clearskies.data.model.response.DailyAndHourlyWeatherResponse
import com.jHerscu.clearskies.data.model.response.YesterdaysWeatherResponse
import com.jHerscu.clearskies.data.source.local.entity.LocalGeocodedCity
import com.jHerscu.clearskies.data.source.local.relation.CityWithDailyForecasts
import com.jHerscu.clearskies.data.source.local.relation.CityWithHourlyForecasts
import kotlinx.coroutines.flow.Flow

interface WeatherRepo {

    suspend fun fetchDailyAndHourlyWeather(city: LocalGeocodedCity): Flow<DailyAndHourlyWeatherResponse>

    suspend fun fetchYesterdaysWeather(
        city: LocalGeocodedCity,
        date: Long
    ): Flow<YesterdaysWeatherResponse>

    suspend fun cacheWeatherData(
        dailyData: CityWithDailyForecasts,
        hourlyData: CityWithHourlyForecasts
    )

    suspend fun deleteWeatherForCity(city: LocalGeocodedCity)

    suspend fun getAllDailyWeatherData(city: LocalGeocodedCity): Flow<CityWithDailyForecasts>

    suspend fun getAllHourlyWeatherData(city: LocalGeocodedCity): Flow<CityWithHourlyForecasts>

}