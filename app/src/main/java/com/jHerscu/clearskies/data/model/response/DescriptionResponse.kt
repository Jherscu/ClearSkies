package com.jHerscu.clearskies.data.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DescriptionResponse(
    val description: String,
    val icon: String,
)
