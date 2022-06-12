package com.jHerscu.clearskies.domain.repoInterface

import com.jHerscu.clearskies.data.source.local.entity.LocalIcon

interface WeatherIconRepo {

    suspend fun cacheIcon(icon: LocalIcon)

}