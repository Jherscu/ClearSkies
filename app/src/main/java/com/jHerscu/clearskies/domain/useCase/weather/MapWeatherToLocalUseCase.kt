package com.jHerscu.clearskies.domain.useCase.weather

import com.jHerscu.clearskies.data.model.response.DailyAndHourlyWeatherResponse
import com.jHerscu.clearskies.data.model.response.YesterdaysWeatherResponse
import com.jHerscu.clearskies.data.source.local.entity.LocalDailyForecast
import com.jHerscu.clearskies.data.source.local.entity.LocalHourlyForecast
import com.jHerscu.clearskies.di.DefaultDispatcher
import com.jHerscu.clearskies.utils.Resource
import com.jHerscu.clearskies.utils.TextWrapper
import com.jHerscu.clearskies.utils.mappers.WeatherForecastMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class MapWeatherToLocalUseCase @Inject constructor(
    private val weatherMapper: WeatherForecastMapper,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(
        dailyAndHourlyWeatherResponse: DailyAndHourlyWeatherResponse,
        yesterdaysWeatherResponse: YesterdaysWeatherResponse,
        qualifiedName: String
    ): Resource<Pair<List<LocalDailyForecast>, List<LocalHourlyForecast>>?> {
        return withContext(defaultDispatcher) {
            try {
                val mapped = weatherMapper.remoteToLocal(
                    currentWeather = dailyAndHourlyWeatherResponse,
                    yesterdaysWeather = yesterdaysWeatherResponse,
                    qualifiedName = qualifiedName
                )
                Resource.Success(data = mapped)
            } catch (e: Exception) {
                Resource.Error(
                    text = e.message.let {
                        if (it != null) {
                            TextWrapper.DynamicString(it)
                        } else {
                            TextWrapper.DynamicString("Unexpected error mapping remote to local.")
                        }
                    }
                )
            }
        }
    }
}