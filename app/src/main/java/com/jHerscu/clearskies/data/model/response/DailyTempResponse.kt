package com.jHerscu.clearskies.data.model.response

import com.squareup.moshi.Json

data class DailyTempResponse(
    @field:Json(name = "min") val minTemp: Float,
    @field:Json(name = "max") val maxTemp: Float
)
