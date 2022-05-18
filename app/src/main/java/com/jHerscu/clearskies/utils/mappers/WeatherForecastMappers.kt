package com.jHerscu.clearskies.utils.mappers

import com.jHerscu.clearskies.data.model.DailyForecast
import com.jHerscu.clearskies.data.model.HourlyForecast
import com.jHerscu.clearskies.data.model.response.DailyAndHourlyWeatherResponse
import com.jHerscu.clearskies.data.model.response.DailyTempResponse
import com.jHerscu.clearskies.data.model.response.DailyWeatherResponse
import com.jHerscu.clearskies.data.model.response.YesterdaysWeatherResponse
import com.jHerscu.clearskies.data.source.local.entity.LocalDailyForecast
import com.jHerscu.clearskies.data.source.local.entity.LocalHourlyForecast

class WeatherForecastMappers {

    /**
     * Takes weather information from [YesterdaysWeatherResponse], and converts it into
     * a standard [DailyWeatherResponse]
     */
    private fun yesterdaysWeatherResponseToDailyWeatherResponse(yesterdaysWeather: YesterdaysWeatherResponse): DailyWeatherResponse {
        yesterdaysWeather.let {

            return DailyWeatherResponse(
                dateInMill = it.current.dateInMill,
                temp = DailyTempResponse(
                    minTemp = it.hourly.minOfOrNull { hourly -> hourly.temp }!!,
                    maxTemp = it.hourly.maxOfOrNull { hourly -> hourly.temp }!!
                ),
                humidity = it.current.humidity,
                weather = it.current.weather
            )
        }
    }

    /**
     * Takes weather information from Api responses and converts them to a pair of lists containing
     * entities to be cached in the room db.
     *
     * @param qualifiedName Name of the city and state code to attach to the entries
     */
    fun remoteToLocal(
        currentWeather: DailyAndHourlyWeatherResponse,
        yesterdaysWeather: YesterdaysWeatherResponse,
        qualifiedName: String
    ): Pair<List<LocalDailyForecast>, List<LocalHourlyForecast>> {
        val hourlyList = currentWeather.hourly.toMutableList().apply {
            this.add(currentWeather.current)
        }

        val dailyList = currentWeather.daily.toMutableList().apply {
            this.add(yesterdaysWeatherResponseToDailyWeatherResponse(yesterdaysWeather))
        }

        val localHourlyList = hourlyList.map { hourlyResponse ->
            hourlyResponse.let {
                LocalHourlyForecast(
                    qualifiedName = qualifiedName,
                    hourInMill = it.hourInMill,
                    humidity = it.humidity,
                    temp = it.temp,
                    feelsLikeTemp = it.feelsLikeTemp,
                    weatherDescription = it.weather.description,
                    iconCode = it.weather.icon
                )
            }
        }

        val localDailyList = dailyList.map { dailyResponse ->
            dailyResponse.let {
                LocalDailyForecast(
                    qualifiedName = qualifiedName,
                    dateInMill = it.dateInMill,
                    humidity = it.humidity,
                    minTemp = it.temp.minTemp,
                    maxTemp = it.temp.maxTemp,
                    weatherDescription = it.weather.description,
                    iconCode = it.weather.icon
                )
            }

        }

        return Pair(localDailyList, localHourlyList)

    }

    /**
     * Retrieves Daily Forecasts from the room db to display in the UI.
     */
    fun localDailyForecastToData(localData: List<LocalDailyForecast>): List<DailyForecast> {
        return localData.map { localDailyForecast ->
            localDailyForecast.let {
                DailyForecast(
                    dateInMill = it.dateInMill,
                    humidity = it.humidity,
                    minTemp = it.minTemp,
                    maxTemp = it.maxTemp,
                    weatherDescription = it.weatherDescription,
                    iconCode = it.iconCode
                )
            }
        }
    }

    /**
     * Retrieves Hourly Forecasts from the room db to display in the UI.
     */
    fun localHourlyForecastToData(localData: List<LocalHourlyForecast>): List<HourlyForecast> {
        return localData.map { localHourlyForecast ->
            localHourlyForecast.let {
                HourlyForecast(
                    hourInMill = it.hourInMill,
                    humidity = it.humidity,
                    temp = it.temp,
                    feelsLikeTemp = it.feelsLikeTemp,
                    weatherDescription = it.weatherDescription,
                    iconCode = it.iconCode
                )
            }
        }
    }

}