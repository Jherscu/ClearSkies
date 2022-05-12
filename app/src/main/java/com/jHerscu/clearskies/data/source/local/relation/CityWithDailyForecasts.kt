package com.jHerscu.clearskies.data.source.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.jHerscu.clearskies.data.source.local.entity.GeocodedCity
import com.jHerscu.clearskies.data.source.local.entity.LocalDailyForecast

data class CityWithDailyForecasts(
    @Embedded val city: GeocodedCity,
    @Relation(
        parentColumn = "qualified_name",
        entityColumn = "qualified_name"
    )
    val localDailyForecasts: List<LocalDailyForecast>

)