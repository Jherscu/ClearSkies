package com.jHerscu.clearskies.data.source.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.jHerscu.clearskies.data.source.local.entity.LocalHourlyForecast
import com.jHerscu.clearskies.data.source.local.entity.LocalIcon

data class HourlyForecastAndIcon(
    @Embedded val icon: LocalIcon,
    @Relation(
        parentColumn = "icon_code",
        entityColumn = "icon_code"
    )
    val forecast: LocalHourlyForecast
)
