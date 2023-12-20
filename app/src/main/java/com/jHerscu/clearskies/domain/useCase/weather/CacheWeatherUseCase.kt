package com.jHerscu.clearskies.domain.useCase.weather

import com.jHerscu.clearskies.data.repo.WeatherRepoImpl
import com.jHerscu.clearskies.data.source.local.entity.LocalDailyForecast
import com.jHerscu.clearskies.data.source.local.entity.LocalHourlyForecast
import com.jHerscu.clearskies.utils.Resource
import com.jHerscu.clearskies.utils.SimpleResource
import com.jHerscu.clearskies.utils.TextWrapper
import timber.log.Timber
import javax.inject.Inject

class CacheWeatherUseCase
    @Inject
    constructor(
        private val weatherRepo: WeatherRepoImpl,
    ) {
        suspend operator fun invoke(
            dailyData: List<LocalDailyForecast>,
            hourlyData: List<LocalHourlyForecast>,
        ): SimpleResource {
            return try {
                weatherRepo.cacheWeatherData(
                    dailyData = dailyData,
                    hourlyData = hourlyData,
                )
                Resource.Success(Unit)
            } catch (e: Exception) {
                Timber.e("Failed to cache weather: ${e.message}")
                Resource.Error(text = TextWrapper.DynamicString(e.stackTraceToString()))
            }
        }
    }
