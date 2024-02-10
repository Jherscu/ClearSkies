package com.jHerscu.clearskies.data.source.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.jHerscu.clearskies.data.source.local.entity.LocalDailyForecast
import com.jHerscu.clearskies.data.source.local.entity.LocalIcon

data class DailyForecastAndIcon(
    @Embedded val forecast: LocalDailyForecast,
    @Relation(
        parentColumn = "icon_code",
        entityColumn = "icon_code",
    )
    val icon: LocalIcon,
)
