package com.jHerscu.clearskies.data.source.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.jHerscu.clearskies.data.source.local.entity.LocalDailyForecast
import com.jHerscu.clearskies.data.source.local.entity.LocalGeocodedCity

data class CityWithDailyForecasts(
    @Embedded val city: LocalGeocodedCity,
    @Relation(
        parentColumn = "qualified_name",
        entityColumn = "qualified_name"
    )
    val localDailyForecasts: List<LocalDailyForecast>

)