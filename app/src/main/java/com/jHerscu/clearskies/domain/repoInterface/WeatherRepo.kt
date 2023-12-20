package com.jHerscu.clearskies.domain.repoInterface

import com.jHerscu.clearskies.data.model.response.UnparsedResponsesHolder
import com.jHerscu.clearskies.data.source.local.entity.LocalDailyForecast
import com.jHerscu.clearskies.data.source.local.entity.LocalGeocodedCity
import com.jHerscu.clearskies.data.source.local.entity.LocalHourlyForecast
import com.jHerscu.clearskies.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepo {
    suspend fun fetchWeatherData(
        city: LocalGeocodedCity,
        date: Long,
    ): Resource<UnparsedResponsesHolder?>

    suspend fun cacheWeatherData(
        dailyData: List<LocalDailyForecast>,
        hourlyData: List<LocalHourlyForecast>,
    )

    suspend fun deleteWeatherForCity(city: LocalGeocodedCity)

    fun getAllDailyWeatherData(city: LocalGeocodedCity): Flow<List<LocalDailyForecast>>

    fun getAllHourlyWeatherData(city: LocalGeocodedCity): Flow<List<LocalHourlyForecast>>
}
