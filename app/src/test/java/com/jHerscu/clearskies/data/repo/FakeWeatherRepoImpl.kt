package com.jHerscu.clearskies.data.repo

import com.jHerscu.clearskies.data.model.response.*
import com.jHerscu.clearskies.data.source.local.ONE_WEEK_IN_MILLIS
import com.jHerscu.clearskies.data.source.local.entity.LocalDailyForecast
import com.jHerscu.clearskies.data.source.local.entity.LocalGeocodedCity
import com.jHerscu.clearskies.data.source.local.entity.LocalHourlyForecast
import com.jHerscu.clearskies.domain.repoInterface.WeatherRepo
import com.jHerscu.clearskies.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

const val TEMP = 65.7f
const val MIN_TEMP = 77.0f
const val MAX_TEMP = 90.5f
const val HUMIDITY = 33
const val CURRENT_TIME_IN_MILLIS = 1655336735000L
const val DAILY_OFFSET = 86400000L
const val HOURLY_OFFSET = 3600000L
const val DESCRIPTION = ""
const val ICON = ""

class FakeWeatherRepoImpl() : WeatherRepo {

    // Start of necessary info to fake fetched weather data
    private val dailyTemps = DailyTempResponse(
        minTemp = MIN_TEMP,
        maxTemp = MAX_TEMP
    )

    private val weatherDescription = DescriptionResponse(description = DESCRIPTION, icon = ICON)

    private val previousDailyWeather = PreviousDailyWeatherResponse(
        dateInMillis = CURRENT_TIME_IN_MILLIS - DAILY_OFFSET,
        temp = TEMP,
        humidity = HUMIDITY,
        weather = weatherDescription
    )

    private val listOfHourlyWeather = listOf(
        HourlyWeatherResponse(
            hourInMillis = CURRENT_TIME_IN_MILLIS + HOURLY_OFFSET,
            temp = TEMP,
            feelsLikeTemp = TEMP,
            humidity = HUMIDITY,
            weather = weatherDescription
        ), HourlyWeatherResponse(
            hourInMillis = CURRENT_TIME_IN_MILLIS + (HOURLY_OFFSET * 2),
            temp = TEMP,
            feelsLikeTemp = TEMP,
            humidity = HUMIDITY,
            weather = weatherDescription
        )
    )

    private val listOfDailyWeather = listOf(
        DailyWeatherResponse(
            dateInMillis = CURRENT_TIME_IN_MILLIS + DAILY_OFFSET,
            temp = dailyTemps,
            humidity = HUMIDITY,
            weather = weatherDescription
        ), DailyWeatherResponse(
            dateInMillis = CURRENT_TIME_IN_MILLIS + (DAILY_OFFSET * 7),
            temp = dailyTemps,
            humidity = HUMIDITY,
            weather = weatherDescription
        )
    )

    private val dailyAndHourly = DailyAndHourlyWeatherResponse(
        current = HourlyWeatherResponse(
            hourInMillis = CURRENT_TIME_IN_MILLIS,
            temp = TEMP,
            feelsLikeTemp = TEMP,
            humidity = HUMIDITY,
            weather = weatherDescription
        ),
        hourly = listOfHourlyWeather,
        daily = listOfDailyWeather
    )

    private val yesterdaysWeather = YesterdaysWeatherResponse(
        current = previousDailyWeather,
        hourly = listOfHourlyWeather
    )
    // End of necessary info to fake fetched weather data

    // Fake db implementation
    private val dailyList = mutableListOf<LocalDailyForecast>()
    private val hourlyList = mutableListOf<LocalHourlyForecast>()

    override suspend fun fetchWeatherData(
        city: LocalGeocodedCity,
        date: Long
    ): Resource<UnparsedResponsesHolder?> {
        return Resource.Success(
            data = UnparsedResponsesHolder(
                dailyAndHourlyWeatherResponse = dailyAndHourly,
                yesterdaysWeatherResponse = yesterdaysWeather
            )
        )
    }

    override suspend fun cacheWeatherData(
        dailyData: List<LocalDailyForecast>,
        hourlyData: List<LocalHourlyForecast>
    ) {
        for (forecast in dailyData) {
            dailyList.add(forecast)
        }
        for (forecast in hourlyData) {
            hourlyList.add(forecast)
        }
    }

    override suspend fun deleteWeatherForCity(qualifiedName: String) {
        dailyList.removeAll(dailyList)
        hourlyList.removeAll(hourlyList)
    }

    override fun getAllDailyWeatherData(qualifiedName: String): Flow<List<LocalDailyForecast>> {
        return flow {
            emit(dailyList)
        }
    }

    override fun getAllHourlyWeatherData(qualifiedName: String): Flow<List<LocalHourlyForecast>> {
        return flow {
            emit(hourlyList)
        }
    }

    override fun validateWeatherExists(qualifiedName: String): Flow<Int> {
        return flow { if (dailyList.size > 0 && hourlyList.size > 0) emit(1) else emit(0) }
    }

    override fun validateWeatherUpToDate(qualifiedName: String, currentDate: Int): Flow<Int> {
        return flow {
            if (dailyList.last().timeInMillis >= CURRENT_TIME_IN_MILLIS + ONE_WEEK_IN_MILLIS) {
                emit(1)
            } else {
                emit(0)
            }
        }
    }

}