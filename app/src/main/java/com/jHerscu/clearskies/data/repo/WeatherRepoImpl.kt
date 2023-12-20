package com.jHerscu.clearskies.data.repo

import com.jHerscu.clearskies.R
import com.jHerscu.clearskies.data.model.response.DailyAndHourlyWeatherResponse
import com.jHerscu.clearskies.data.model.response.UnparsedResponsesHolder
import com.jHerscu.clearskies.data.model.response.YesterdaysWeatherResponse
import com.jHerscu.clearskies.data.source.local.WeatherDao
import com.jHerscu.clearskies.data.source.local.entity.LocalDailyForecast
import com.jHerscu.clearskies.data.source.local.entity.LocalGeocodedCity
import com.jHerscu.clearskies.data.source.local.entity.LocalHourlyForecast
import com.jHerscu.clearskies.data.source.remote.WeatherApiService
import com.jHerscu.clearskies.di.IoDispatcher
import com.jHerscu.clearskies.domain.repoInterface.WeatherRepo
import com.jHerscu.clearskies.utils.Resource
import com.jHerscu.clearskies.utils.TextWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class WeatherRepoImpl
    @Inject
    constructor(
        private val weatherDao: WeatherDao,
        private val weatherApiService: WeatherApiService,
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    ) : WeatherRepo {
        private suspend fun fetchDailyAndHourlyWeather(city: LocalGeocodedCity): Response<DailyAndHourlyWeatherResponse> {
            return with(city) {
                weatherApiService.getOneCallWeather(
                    lat = latitude,
                    lon = longitude,
                )
            }
        }

        private suspend fun fetchYesterdaysWeather(
            city: LocalGeocodedCity,
            date: Long,
        ): Response<YesterdaysWeatherResponse> {
            return with(city) {
                weatherApiService.getYesterdaysWeather(
                    lat = latitude,
                    lon = longitude,
                    yesterdaysDate = date,
                )
            }
        }

        override suspend fun fetchWeatherData(
            city: LocalGeocodedCity,
            date: Long,
        ): Resource<UnparsedResponsesHolder?> {
            return try {
                // Launches on main dispatcher by default.
                // Will cancel all child coroutines and propagate exceptions up when any
                // child coroutine fails
                coroutineScope {
                    withContext(ioDispatcher) {
                        val yesterdaysWeatherResponse =
                            async {
                                fetchYesterdaysWeather(
                                    city = city,
                                    date = date,
                                )
                            }

                        val dailyAndHourlyWeatherResponse =
                            async {
                                fetchDailyAndHourlyWeather(
                                    city = city,
                                )
                            }

                        if (yesterdaysWeatherResponse.await().isSuccessful && dailyAndHourlyWeatherResponse.await().isSuccessful) {
                            Resource.Success(
                                // TODO(jherscu): rm !!s
                                UnparsedResponsesHolder(
                                    dailyAndHourlyWeatherResponse =
                                        dailyAndHourlyWeatherResponse.await()
                                            .body()!!,
                                    yesterdaysWeatherResponse =
                                        yesterdaysWeatherResponse.await()
                                            .body()!!,
                                ),
                            )
                        } else {
                            Resource.Error(
                                text =
                                    TextWrapper.DynamicString(
                                        "YesterdaysResponse: ${
                                            yesterdaysWeatherResponse.await().message()
                                        }\n" +
                                            "DailyAndHourlyResponse: ${
                                                dailyAndHourlyWeatherResponse.await().message()
                                            }",
                                    ),
                            )
                        }
                    }
                }
            } catch (e: IOException) {
                // TODO(jherscu): write catchNetworkError util fun to reuse
                Resource.Error(text = TextWrapper.StringResource(R.string.bad_connection))
            } catch (e: HttpException) {
                Resource.Error(text = TextWrapper.StringResource(R.string.bad_response))
            }
        }

        override suspend fun cacheWeatherData(
            dailyData: List<LocalDailyForecast>,
            hourlyData: List<LocalHourlyForecast>,
        ) {
            with(weatherDao) {
                for (forecast in dailyData) {
                    upsertDailyForecast(forecast)
                }
                for (forecast in hourlyData) {
                    upsertHourlyForecast(forecast)
                }
            }
        }

        override suspend fun deleteWeatherForCity(city: LocalGeocodedCity) {
            weatherDao.run {
                getAllHourlyWeatherData(city).collect { list ->
                    for (forecast in list) {
                        deleteHourlyForecast(forecast)
                    }
                }
                getAllDailyWeatherData(city).collect { list ->
                    for (forecast in list) {
                        deleteDailyForecast(forecast)
                    }
                }
            }
        }

        override fun getAllDailyWeatherData(city: LocalGeocodedCity): Flow<List<LocalDailyForecast>> {
            return weatherDao.getAllDailyForecastsByCity(city.qualifiedName).distinctUntilChanged()
        }

        override fun getAllHourlyWeatherData(city: LocalGeocodedCity): Flow<List<LocalHourlyForecast>> {
            return weatherDao.getAllHourlyForecastsByCity(city.qualifiedName).distinctUntilChanged()
        }
    }
