package com.jHerscu.clearskies.data.source.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.jHerscu.clearskies.data.source.local.entity.GeocodedCity
import com.jHerscu.clearskies.data.source.local.entity.WeatherForecast

data class CityWithWeatherForecasts(
    @Embedded val city: GeocodedCity,
    @Relation(
        parentColumn = "qualified_name",
        entityColumn = "qualified_name"
    )
    val weatherForecasts: List<WeatherForecast>
)

val TODO: Any = TODO("Create daily and hourly subsets of weatherforecast")