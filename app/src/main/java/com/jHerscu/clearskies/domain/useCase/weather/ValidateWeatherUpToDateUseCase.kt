package com.jHerscu.clearskies.domain.useCase.weather

import com.jHerscu.clearskies.domain.repoInterface.WeatherRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ValidateWeatherUpToDateUseCase @Inject constructor(
    private val weatherRepo: WeatherRepo
) {
    operator fun invoke(
        qualifiedName: String,
        currentDate: Int
    ): Flow<Int> {
        return weatherRepo.validateWeatherUpToDate(
            qualifiedName = qualifiedName,
            currentDate = currentDate
        )
    }
}