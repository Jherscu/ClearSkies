package com.jHerscu.clearskies.domain.useCase.location

import com.jHerscu.clearskies.data.repo.GeocodeRepoImpl
import com.jHerscu.clearskies.data.source.local.entity.LocalGeocodedCity
import javax.inject.Inject

class GetCityByNameUseCase
    @Inject
    constructor(
        private val geocodeRepoImpl: GeocodeRepoImpl,
    ) {
        suspend operator fun invoke(query: String): LocalGeocodedCity {
            return geocodeRepoImpl.getGeocodedCity(query)
        }
    }
