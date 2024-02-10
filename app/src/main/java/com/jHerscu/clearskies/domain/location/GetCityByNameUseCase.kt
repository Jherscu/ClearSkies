package com.jHerscu.clearskies.domain.location

import com.jHerscu.clearskies.data.repo.GeocodeRepo
import com.jHerscu.clearskies.data.source.local.entity.LocalGeocodedCity
import javax.inject.Inject

class GetCityByNameUseCase
    @Inject
    constructor(
        private val geocodeRepo: GeocodeRepo,
    ) {
        suspend operator fun invoke(query: String): LocalGeocodedCity {
            return geocodeRepo.getGeocodedCity(query)
        }
    }
