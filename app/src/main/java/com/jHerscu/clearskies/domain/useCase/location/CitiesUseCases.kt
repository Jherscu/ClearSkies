package com.jHerscu.clearskies.domain.useCase.location

data class CitiesUseCases(
    val cacheNewCityUseCase: CacheNewCityUseCase,
    val deleteCityWithWeatherUseCase: DeleteCityWithWeatherUseCase,
    val getAllCitiesUseCase: GetAllCitiesUseCase,
    val getCityOptionsUseCase: GetCityOptionsUseCase,
    val getCityByNameUseCaseUseCase: GetCityByNameUseCase,
)
