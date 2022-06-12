package com.jHerscu.clearskies.domain.repoInterface

import com.jHerscu.clearskies.data.model.response.SuggestedCitiesResponse
import com.jHerscu.clearskies.data.source.local.entity.LocalGeocodedCity
import com.jHerscu.clearskies.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GeocodeRepo {

    suspend fun fetchCityList(query: String): Resource<SuggestedCitiesResponse?>

    suspend fun getGeocodedCity(qualifiedName: String): LocalGeocodedCity

    suspend fun cacheGeocodedCity(city: LocalGeocodedCity)

    suspend fun deleteCity(city: LocalGeocodedCity) // Also delete any weather data associated with that city (Logic in use case)

    suspend fun getListOfGeocodedCities(): Flow<List<LocalGeocodedCity>>

}