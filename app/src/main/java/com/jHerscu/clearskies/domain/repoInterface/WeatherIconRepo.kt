package com.jHerscu.clearskies.domain.repoInterface

import android.graphics.drawable.BitmapDrawable
import com.jHerscu.clearskies.data.source.local.entity.LocalIcon
import kotlinx.coroutines.flow.Flow

interface WeatherIconRepo {

    suspend fun cacheIcon(icon: LocalIcon)

    suspend fun getIcon(icon: LocalIcon): Flow<BitmapDrawable>

    suspend fun deleteIcon(icon: LocalIcon)

}