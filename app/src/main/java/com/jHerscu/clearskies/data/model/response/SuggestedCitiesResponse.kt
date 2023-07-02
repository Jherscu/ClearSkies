package com.jHerscu.clearskies.data.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SuggestedCitiesResponse(
    val addresses: List<CityResponse>
)
