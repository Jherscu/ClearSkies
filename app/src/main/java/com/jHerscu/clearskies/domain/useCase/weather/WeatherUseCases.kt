package com.jHerscu.clearskies.domain.useCase.weather

import com.jHerscu.clearskies.domain.useCase.weather.icon.CacheIconUseCase
import com.jHerscu.clearskies.domain.useCase.weather.icon.GetIconUseCase

data class WeatherUseCases(
    val getWeatherDataUseCase: GetWeatherDataUseCase,
    val fetchAllWeatherUseCase: FetchAllWeatherUseCase,
    val cacheWeatherUseCase: CacheWeatherUseCase,
    val getIconUseCase: GetIconUseCase,
    val cacheIconUseCase: CacheIconUseCase,
    val mapWeatherToLocalUseCase: MapWeatherToLocalUseCase,
    val validateWeatherExistsUseCase: ValidateWeatherExistsUseCase,
    val validateWeatherUpToDateUseCase: ValidateWeatherUpToDateUseCase
)
