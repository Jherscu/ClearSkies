package com.jHerscu.clearskies.domain.weather

import com.jHerscu.clearskies.domain.weather.icon.CacheIconUseCase
import com.jHerscu.clearskies.domain.weather.icon.GetIconUseCase

data class WeatherUseCases(
    val getWeatherDataUseCase: GetWeatherDataUseCase,
    val fetchAllWeatherUseCase: FetchAllWeatherUseCase,
    val cacheWeatherUseCase: CacheWeatherUseCase,
    val getIconUseCase: GetIconUseCase,
    val cacheIconUseCase: CacheIconUseCase,
    val mapWeatherToLocalUseCase: MapWeatherToLocalUseCase,
    val validateWeatherExistsUseCase: ValidateWeatherExistsUseCase,
    val validateWeatherUpToDateUseCase: ValidateWeatherUpToDateUseCase,
)
