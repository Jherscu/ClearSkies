package com.jHerscu.clearskies.data.source.remote

import com.jHerscu.clearskies.BuildConfig
import com.jHerscu.clearskies.data.model.response.DailyAndHourlyWeatherResponse
import com.jHerscu.clearskies.data.model.response.IconResponse
import com.jHerscu.clearskies.data.model.response.YesterdaysWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryName

private const val CURRENT_WEATHER_URL = "data/3.0/onecall"

private const val HISTORIC_WEATHER_URL = "/timemachine"

private const val IMAGE_WEATHER_URL = "img/wn/"

interface WeatherApiService { // TODO(jherscu): test!

    /**
     * Retrieve upcoming Daily forecasts, current Daily forecast, current Hourly forecast,
     * and upcoming Hourly forecasts.
     */
    @GET(CURRENT_WEATHER_URL)
    suspend fun getOneCallWeather(
        @Query(value = "lat")
        lat: Float,
        @Query(value = "lon")
        lon: Float,
        @Query(value = "exclude")
        exclude: List<String> = listOf("minutely", "alerts"),
        @Query(value = "units")
        units: String = "imperial",
        @Query(value = "appid")
        appId: String = BuildConfig.OPEN_WEATHER_KEY
    ): Response<DailyAndHourlyWeatherResponse>

    /**
     * Retrieve previous day's Daily and Hourly forecast. No min/max temp data is provided so the
     * highest and lowest values must be sorted from hourly temps to find the daily high and low.
     */
    @GET("$CURRENT_WEATHER_URL$HISTORIC_WEATHER_URL")
    suspend fun getYesterdaysWeather( // TODO(jherscu): add exclude param?
        @Query(value = "lat")
        lat: Float,
        @Query(value = "lon")
        lon: Float,
        @Query(value = "dt")
        yesterdaysDate: Long, // Calendar Instance get previous day in mill
        @Query(value = "units")
        units: String = "imperial",
        @Query(value = "appid")
        appId: String = BuildConfig.OPEN_WEATHER_KEY
    ): Response<YesterdaysWeatherResponse>

    /**
     * Grab icon from image code.
     */
    @GET(IMAGE_WEATHER_URL)
    suspend fun getIconFromWeatherCode(
        @QueryName
        iconCode: String // Pass input through formatter "$iconCode@2x.png"
    ): Response<IconResponse>
}
