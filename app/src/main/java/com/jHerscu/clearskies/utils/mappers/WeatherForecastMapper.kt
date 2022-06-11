package com.jHerscu.clearskies.utils.mappers

import com.jHerscu.clearskies.data.model.DailyForecast
import com.jHerscu.clearskies.data.model.HourlyForecast
import com.jHerscu.clearskies.data.model.response.DailyAndHourlyWeatherResponse
import com.jHerscu.clearskies.data.model.response.DailyTempResponse
import com.jHerscu.clearskies.data.model.response.DailyWeatherResponse
import com.jHerscu.clearskies.data.model.response.YesterdaysWeatherResponse
import com.jHerscu.clearskies.data.source.local.WeatherDao
import com.jHerscu.clearskies.data.source.local.entity.LocalDailyForecast
import com.jHerscu.clearskies.data.source.local.entity.LocalHourlyForecast
import javax.inject.Inject

class WeatherForecastMapper @Inject constructor(
    private val weatherDao: WeatherDao
) {

    /**
     * Takes weather information from [YesterdaysWeatherResponse], and converts it into
     * a standard [DailyWeatherResponse]
     */
    private fun yesterdaysWeatherResponseToDailyWeatherResponse(yesterdaysWeather: YesterdaysWeatherResponse): DailyWeatherResponse {
        with(yesterdaysWeather) {
            return DailyWeatherResponse(
                dateInMill = current.dateInMill,
                temp = DailyTempResponse(
                    minTemp = hourly.minOfOrNull { hourly -> hourly.temp }!!,
                    maxTemp = hourly.maxOfOrNull { hourly -> hourly.temp }!!
                ),
                humidity = current.humidity,
                weather = current.weather
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
            with(hourlyResponse) {
                LocalHourlyForecast(
                    qualifiedName = qualifiedName,
                    timeInMill = hourInMill,
                    humidity = humidity,
                    temp = temp,
                    feelsLikeTemp = feelsLikeTemp,
                    weatherDescription = weather.description,
                    iconCode = weather.icon
                )
            }
        }

        val localDailyList = dailyList.map { dailyResponse ->
            with(dailyResponse) {
                LocalDailyForecast(
                    qualifiedName = qualifiedName,
                    timeInMill = dateInMill,
                    humidity = humidity,
                    minTemp = temp.minTemp,
                    maxTemp = temp.maxTemp,
                    weatherDescription = weather.description,
                    iconCode = weather.icon
                )
            }

        }

        return Pair(localDailyList, localHourlyList)

    }

    /**
     * Retrieves Daily Forecasts from the room db to display in the UI.
     */
    suspend fun localDailyForecastToData(localData: List<LocalDailyForecast>): List<DailyForecast> {
        return localData.map { localDailyForecast ->
            with(localDailyForecast) {
                DailyForecast(
                    dateInMill = timeInMill,
                    humidity = humidity,
                    minTemp = minTemp,
                    maxTemp = maxTemp,
                    weatherDescription = weatherDescription,
                    icon = weatherDao.getDailyForecastAndIcon(iconCode).icon.iconBitmap
                )
            }
        }
    }

    /**
     * Retrieves Hourly Forecasts from the room db to display in the UI.
     */
    suspend fun localHourlyForecastToData(localData: List<LocalHourlyForecast>): List<HourlyForecast> {
        return localData.map { localHourlyForecast ->
            with(localHourlyForecast) {
                HourlyForecast(
                    hourInMill = timeInMill,
                    humidity = humidity,
                    temp = temp,
                    feelsLikeTemp = feelsLikeTemp,
                    weatherDescription = weatherDescription,
                    icon = weatherDao.getHourlyForecastAndIcon(iconCode).icon.iconBitmap
                )
            }
        }
    }

}