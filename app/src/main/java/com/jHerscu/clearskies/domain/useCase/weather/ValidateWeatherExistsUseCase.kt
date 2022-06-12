package com.jHerscu.clearskies.domain.useCase.weather

import com.jHerscu.clearskies.data.source.local.WeatherDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ValidateWeatherExistsUseCase @Inject constructor(
    private val weatherDao: WeatherDao
) {
    operator fun invoke(
        qualifiedName: String
    ): Flow<Int> {
        return weatherDao.validateDataExistsByCity(
            qualifiedName = qualifiedName
        )
    }
}