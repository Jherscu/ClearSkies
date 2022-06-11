package com.jHerscu.clearskies.data.source.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.jHerscu.clearskies.data.source.local.entity.LocalHourlyForecast
import com.jHerscu.clearskies.data.source.local.entity.LocalIcon

data class HourlyForecastAndIcon(
    @Embedded val forecast: LocalHourlyForecast,
    @Relation(
        parentColumn = "icon_code",
        entityColumn = "icon_code"
    )
    val icon: LocalIcon
)
