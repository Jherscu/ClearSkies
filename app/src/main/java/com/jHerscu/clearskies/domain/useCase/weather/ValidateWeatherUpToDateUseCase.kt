package com.jHerscu.clearskies.domain.useCase.weather

import com.jHerscu.clearskies.data.source.local.WeatherDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ValidateWeatherUpToDateUseCase @Inject constructor(
    private val weatherDao: WeatherDao
) {
    operator fun invoke(
        qualifiedName: String,
        currentDate: Int
    ): Flow<Boolean> {
        return weatherDao.upToDateCityInfoExists(
            qualifiedName = qualifiedName,
            currentDate = currentDate
        )
    }
}
