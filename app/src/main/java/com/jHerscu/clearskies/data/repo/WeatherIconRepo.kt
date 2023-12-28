package com.jHerscu.clearskies.data.repo

import com.jHerscu.clearskies.data.source.local.WeatherDao
import com.jHerscu.clearskies.data.source.local.entity.LocalIcon
import javax.inject.Inject

class WeatherIconRepo
    @Inject
    constructor(
        private val dao: WeatherDao,
    ) {
        suspend fun cacheIcon(icon: LocalIcon) {
            dao.upsertLocalIcon(icon)
        }
    }
