package com.jHerscu.clearskies.data.source.remote

import co.infinum.retromock.meta.MockResponse
import com.jHerscu.clearskies.data.model.response.SuggestedCitiesResponse
import retrofit2.Response
import retrofit2.http.GET

interface FakeGeocodeApiService {

    @GET()
    @MockResponse // TODO() Set Body and set second call to be error
    @co.infinum.retromock.meta.Mock
    suspend fun getListOfCities(): Response<SuggestedCitiesResponse>

}