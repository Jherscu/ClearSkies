package com.jHerscu.clearskies.domain.useCase.location

import com.jHerscu.clearskies.data.source.local.entity.LocalGeocodedCity
import com.jHerscu.clearskies.domain.repoInterface.GeocodeRepo
import javax.inject.Inject

class GetCityByNameUseCase @Inject constructor(
    private val geocodeRepo: GeocodeRepo
) {
    suspend operator fun invoke(query: String): LocalGeocodedCity {
        return geocodeRepo.getGeocodedCity(query)
    }
}