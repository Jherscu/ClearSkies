package com.jHerscu.clearskies

import co.infinum.retromock.meta.MockResponse
import com.jHerscu.clearskies.data.model.response.DailyAndHourlyWeatherResponse
import com.jHerscu.clearskies.data.model.response.IconResponse
import com.jHerscu.clearskies.data.model.response.YesterdaysWeatherResponse
import com.jHerscu.clearskies.data.source.remote.CURRENT_WEATHER_URL
import com.jHerscu.clearskies.data.source.remote.HISTORIC_WEATHER_URL
import com.jHerscu.clearskies.data.source.remote.IMAGE_WEATHER_URL
import retrofit2.Response
import retrofit2.http.GET

interface FakeWeatherApiService {

    @GET(CURRENT_WEATHER_URL)
    @MockResponse // TODO() Set Body and set second call to be error
    @co.infinum.retromock.meta.Mock
    suspend fun getOneCallWeather(): Response<DailyAndHourlyWeatherResponse>

    @GET("$CURRENT_WEATHER_URL$HISTORIC_WEATHER_URL")
    @MockResponse // TODO() Set Body and set second call to be error
    @co.infinum.retromock.meta.Mock
    suspend fun getYesterdaysWeather(): Response<YesterdaysWeatherResponse>

    @GET(IMAGE_WEATHER_URL)
    @MockResponse // TODO() Set Body and set second call to be error
    @co.infinum.retromock.meta.Mock
    suspend fun getIconFromWeatherCode(): Response<IconResponse>

}