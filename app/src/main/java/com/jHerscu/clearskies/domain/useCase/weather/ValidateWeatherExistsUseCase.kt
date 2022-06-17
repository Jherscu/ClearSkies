package com.jHerscu.clearskies.domain.useCase.weather

import com.jHerscu.clearskies.domain.repoInterface.WeatherRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ValidateWeatherExistsUseCase @Inject constructor(
    private val weatherRepo: WeatherRepo
) {
    operator fun invoke(
        qualifiedName: String
    ): Flow<Int> {
        return weatherRepo.validateWeatherExists(
            qualifiedName = qualifiedName
        )
    }
}