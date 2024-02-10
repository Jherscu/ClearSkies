package com.jHerscu.clearskies.data.source.remote

import com.jHerscu.clearskies.data.model.response.SuggestedCitiesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val GEOCODE_AUTOCOMPLETE_URL = "v1/search/autocomplete"

interface GeocodeApiService {
    @GET(GEOCODE_AUTOCOMPLETE_URL)
    suspend fun getListOfCities(
        @Query(value = "query")
        query: String,
        @Query(value = "layers")
        layer: String = "locality",
        @Query(value = "country")
        country: String = "US",
    ): Response<SuggestedCitiesResponse>
}
