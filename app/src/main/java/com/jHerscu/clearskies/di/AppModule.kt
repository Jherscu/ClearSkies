package com.jHerscu.clearskies.di

import com.jHerscu.clearskies.data.source.local.WeatherDao
import com.jHerscu.clearskies.domain.useCase.location.*
import com.jHerscu.clearskies.domain.useCase.weather.*
import com.jHerscu.clearskies.domain.useCase.weather.icon.CacheIconUseCase
import com.jHerscu.clearskies.domain.useCase.weather.icon.GetIconUseCase
import com.jHerscu.clearskies.utils.mappers.GeocodedCityMapper
import com.jHerscu.clearskies.utils.mappers.WeatherForecastMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideWeatherMapper(
        weatherDao: WeatherDao
    ): WeatherForecastMapper {
        return WeatherForecastMapper(
            weatherDao = weatherDao
        )
    }

    @Singleton
    @Provides
    fun provideCityMapper(): GeocodedCityMapper {
        return GeocodedCityMapper()
    }

    @Singleton
    @Provides
    fun provideWeatherUseCases(
        getWeatherDataUseCase: GetWeatherDataUseCase,
        fetchAllWeatherUseCase: FetchAllWeatherUseCase,
        cacheWeatherUseCase: CacheWeatherUseCase,
        getIconUseCase: GetIconUseCase,
        cacheIconUseCase: CacheIconUseCase,
        mapWeatherToLocalUseCase: MapWeatherToLocalUseCase,
        validateWeatherExistsUseCase: ValidateWeatherExistsUseCase,
        validateWeatherUpToDateUseCase: ValidateWeatherUpToDateUseCase
    ): WeatherUseCases {
        return WeatherUseCases(
            getWeatherDataUseCase = getWeatherDataUseCase,
            fetchAllWeatherUseCase = fetchAllWeatherUseCase,
            cacheWeatherUseCase = cacheWeatherUseCase,
            getIconUseCase = getIconUseCase,
            cacheIconUseCase = cacheIconUseCase,
            mapWeatherToLocalUseCase = mapWeatherToLocalUseCase,
            validateWeatherExistsUseCase = validateWeatherExistsUseCase,
            validateWeatherUpToDateUseCase = validateWeatherUpToDateUseCase
        )
    }

    @Singleton
    @Provides
    fun provideCityUseCases(
        cacheNewCityUseCase: CacheNewCityUseCase,
        deleteCityWithWeatherUseCase: DeleteCityWithWeatherUseCase,
        getAllCitiesUseCase: GetAllCitiesUseCase,
        getCityOptionsUseCase: GetCityOptionsUseCase,
        getCityByNameUseCase: GetCityByNameUseCase
    ): CitiesUseCases {
        return CitiesUseCases(
            cacheNewCityUseCase = cacheNewCityUseCase,
            deleteCityWithWeatherUseCase = deleteCityWithWeatherUseCase,
            getAllCitiesUseCase = getAllCitiesUseCase,
            getCityOptionsUseCase = getCityOptionsUseCase,
            getCityByNameUseCaseUseCase = getCityByNameUseCase
        )
    }

}