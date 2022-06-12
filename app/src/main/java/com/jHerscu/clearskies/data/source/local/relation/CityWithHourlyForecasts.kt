package com.jHerscu.clearskies.data.source.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.jHerscu.clearskies.data.source.local.entity.LocalGeocodedCity
import com.jHerscu.clearskies.data.source.local.entity.LocalHourlyForecast

data class CityWithHourlyForecasts(
    @Embedded val city: LocalGeocodedCity,
    @Relation(
        parentColumn = "qualified_name",
        entityColumn = "qualified_name"
    )
    val localHourlyForecasts: List<LocalHourlyForecast>

)