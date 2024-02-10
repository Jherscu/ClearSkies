package com.jHerscu.clearskies.domain.weather

import com.jHerscu.clearskies.data.model.response.UnparsedResponsesHolder
import com.jHerscu.clearskies.data.repo.WeatherRepo
import com.jHerscu.clearskies.data.source.local.entity.LocalGeocodedCity
import com.jHerscu.clearskies.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.Period
import javax.inject.Inject

class FetchAllWeatherUseCase
    @Inject
    constructor(
        private val weatherRepo: WeatherRepo,
    ) {
        suspend operator fun invoke(localCity: LocalGeocodedCity): Resource<UnparsedResponsesHolder?> {
            return withContext(Dispatchers.IO) {
                val yesterday =
                    Instant // TODO(jherscu): Confirm Displays correct time
                        .now()
                        .minus(Period.ofDays(1))
                        .toEpochMilli()

                weatherRepo.fetchWeatherData(localCity, yesterday)
            }
        }
    }
