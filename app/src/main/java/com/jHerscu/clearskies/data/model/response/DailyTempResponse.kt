package com.jHerscu.clearskies.data.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DailyTempResponse(
    @Json(name = "min")
    val minTemp: Float,
    @Json(name = "max")
    val maxTemp: Float,
)
