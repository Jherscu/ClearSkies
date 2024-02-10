package com.jHerscu.clearskies.di

import com.jHerscu.clearskies.data.source.local.WeatherDao
import com.jHerscu.clearskies.domain.location.CacheNewCityUseCase
import com.jHerscu.clearskies.domain.location.CitiesUseCases
import com.jHerscu.clearskies.domain.location.DeleteCityWithWeatherUseCase
import com.jHerscu.clearskies.domain.location.GetAllCitiesUseCase
import com.jHerscu.clearskies.domain.location.GetCityByNameUseCase
import com.jHerscu.clearskies.domain.location.GetCityOptionsUseCase
import com.jHerscu.clearskies.domain.weather.CacheWeatherUseCase
import com.jHerscu.clearskies.domain.weather.FetchAllWeatherUseCase
import com.jHerscu.clearskies.domain.weather.GetWeatherDataUseCase
import com.jHerscu.clearskies.domain.weather.MapWeatherToLocalUseCase
import com.jHerscu.clearskies.domain.weather.ValidateWeatherExistsUseCase
import com.jHerscu.clearskies.domain.weather.ValidateWeatherUpToDateUseCase
import com.jHerscu.clearskies.domain.weather.WeatherUseCases
import com.jHerscu.clearskies.domain.weather.icon.CacheIconUseCase
import com.jHerscu.clearskies.domain.weather.icon.GetIconUseCase
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
    fun provideWeatherMapper(weatherDao: WeatherDao): WeatherForecastMapper { // TODO(jherscu): rm provides
        return WeatherForecastMapper(
            weatherDao = weatherDao,
        )
    }

    @Singleton
    @Provides
    fun provideCityMapper(): GeocodedCityMapper { // TODO(jherscu): rm provides
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
        validateWeatherUpToDateUseCase: ValidateWeatherUpToDateUseCase,
    ): WeatherUseCases {
        return WeatherUseCases(
            getWeatherDataUseCase = getWeatherDataUseCase,
            fetchAllWeatherUseCase = fetchAllWeatherUseCase,
            cacheWeatherUseCase = cacheWeatherUseCase,
            getIconUseCase = getIconUseCase,
            cacheIconUseCase = cacheIconUseCase,
            mapWeatherToLocalUseCase = mapWeatherToLocalUseCase,
            validateWeatherExistsUseCase = validateWeatherExistsUseCase,
            validateWeatherUpToDateUseCase = validateWeatherUpToDateUseCase,
        )
    }

    @Singleton
    @Provides
    fun provideCityUseCases(
        cacheNewCityUseCase: CacheNewCityUseCase,
        deleteCityWithWeatherUseCase: DeleteCityWithWeatherUseCase,
        getAllCitiesUseCase: GetAllCitiesUseCase,
        getCityOptionsUseCase: GetCityOptionsUseCase,
        getCityByNameUseCase: GetCityByNameUseCase,
    ): CitiesUseCases {
        return CitiesUseCases(
            cacheNewCityUseCase = cacheNewCityUseCase,
            deleteCityWithWeatherUseCase = deleteCityWithWeatherUseCase,
            getAllCitiesUseCase = getAllCitiesUseCase,
            getCityOptionsUseCase = getCityOptionsUseCase,
            getCityByNameUseCaseUseCase = getCityByNameUseCase,
        )
    }
}
