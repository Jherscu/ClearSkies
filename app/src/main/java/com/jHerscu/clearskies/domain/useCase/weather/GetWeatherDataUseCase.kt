package com.jHerscu.clearskies.domain.useCase.weather

import com.jHerscu.clearskies.R
import com.jHerscu.clearskies.data.model.Forecast
import com.jHerscu.clearskies.data.source.local.WeatherDao
import com.jHerscu.clearskies.data.source.local.entity.LocalDailyForecast
import com.jHerscu.clearskies.utils.Resource
import com.jHerscu.clearskies.utils.TextWrapper
import com.jHerscu.clearskies.utils.mappers.WeatherForecastMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class GetWeatherDataUseCase
    @Inject
    constructor(
        private val weatherDao: WeatherDao,
        private val mapper: WeatherForecastMapper,
    ) {
        suspend operator fun invoke(
            daily: Boolean,
            qualifiedName: String,
            weatherInCache: Boolean,
            weatherIsRecent: Boolean,
        ): Flow<Resource<List<Forecast>?>> {
            // Returns flow of Resource.Success w/ city with daily or hourly forecasts or Resource.Error w/ error message
            return if (weatherInCache) {
                when (daily) {
                    true -> {
                        weatherDao.getAllDailyForecastsByCity(qualifiedName)
                            .distinctUntilChanged()
                            .transform<List<LocalDailyForecast>, List<Forecast>> { list ->
                                mapper.localDailyForecastToData(list)
                            }
                    }
                    false -> {
                        weatherDao.getAllHourlyForecastsByCity(qualifiedName)
                            .distinctUntilChanged()
                            .transform { list ->
                                mapper.localHourlyForecastToData(list)
                            }
                    }
                }.transform { list: List<Forecast> ->

                    if (weatherIsRecent) {
                        Resource.Success(data = list)
                    } else {
                        Resource.Success(
                            data = list,
                            text = TextWrapper.StringResource(R.string.weather_is_old),
                        )
                    }
                }
            } else {
                flowOf(Resource.Error(text = TextWrapper.StringResource(R.string.weather_not_cached)))
            }
        }
    }
