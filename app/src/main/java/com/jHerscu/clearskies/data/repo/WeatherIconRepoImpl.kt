package com.jHerscu.clearskies.data.repo

import com.jHerscu.clearskies.data.source.local.WeatherDao
import com.jHerscu.clearskies.data.source.local.entity.LocalIcon
import com.jHerscu.clearskies.domain.repoInterface.WeatherIconRepo
import javax.inject.Inject

class WeatherIconRepoImpl @Inject constructor(
    private val dao: WeatherDao
) : WeatherIconRepo {

    override suspend fun cacheIcon(icon: LocalIcon) {
        dao.upsertLocalIcon(icon)
    }
}
