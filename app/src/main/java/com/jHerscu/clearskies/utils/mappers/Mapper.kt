package com.jHerscu.clearskies.utils.mappers

import com.jHerscu.clearskies.data.model.DailyForecast
import com.jHerscu.clearskies.data.model.GeocodedCity
import com.jHerscu.clearskies.data.model.HourlyForecast
import com.jHerscu.clearskies.data.model.response.CityResponse
import com.jHerscu.clearskies.data.model.response.DailyAndHourlyWeatherResponse
import com.jHerscu.clearskies.data.model.response.YesterdaysWeatherResponse
import com.jHerscu.clearskies.data.source.local.entity.LocalDailyForecast
import com.jHerscu.clearskies.data.source.local.entity.LocalGeocodedCity
import com.jHerscu.clearskies.data.source.local.entity.LocalHourlyForecast

sealed interface Mapper {
    interface Geocode : Mapper {
        fun remoteToLocal(remoteData: CityResponse): LocalGeocodedCity

        fun localToData(localData: LocalGeocodedCity): GeocodedCity
    }

    interface Weather : Mapper {
        fun remoteToLocal(
            currentWeather: DailyAndHourlyWeatherResponse,
            yesterdaysWeather: YesterdaysWeatherResponse,
            qualifiedName: String
        ): Pair<List<LocalDailyForecast>, List<LocalHourlyForecast>>

        suspend fun localDailyForecastToData(localData: List<LocalDailyForecast>): List<DailyForecast>

        suspend fun localHourlyForecastToData(localData: List<LocalHourlyForecast>): List<HourlyForecast>
    }
}