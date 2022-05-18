package com.jHerscu.clearskies.domain.repoInterface

import com.jHerscu.clearskies.data.model.GeocodedCity
import com.jHerscu.clearskies.data.model.response.SuggestedCitiesResponse
import com.jHerscu.clearskies.data.source.local.entity.LocalGeocodedCity
import kotlinx.coroutines.flow.Flow

interface GeocodeRepo {

    suspend fun fetchCityList(query: String): Flow<SuggestedCitiesResponse>

    suspend fun getGeocodedCity(city: LocalGeocodedCity): Flow<GeocodedCity>

    suspend fun cacheGeocodedCity(city: LocalGeocodedCity)

    suspend fun deleteCity(city: LocalGeocodedCity) // Also delete any weather data associated with that city (Logic in use case)

    suspend fun getListOfGeocodedCities(): Flow<List<LocalGeocodedCity>>

}