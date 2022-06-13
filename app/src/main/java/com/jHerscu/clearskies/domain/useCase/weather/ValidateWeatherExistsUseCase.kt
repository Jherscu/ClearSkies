package com.jHerscu.clearskies.domain.useCase.weather

import com.jHerscu.clearskies.data.repo.WeatherRepoImpl
import com.jHerscu.clearskies.data.source.local.WeatherDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ValidateWeatherExistsUseCase @Inject constructor(
    private val weatherRepo: WeatherRepoImpl
) {
    operator fun invoke(
        qualifiedName: String
    ): Flow<Int> {
        return weatherRepo.validateWeatherExists(
            qualifiedName = qualifiedName
        )
    }
}