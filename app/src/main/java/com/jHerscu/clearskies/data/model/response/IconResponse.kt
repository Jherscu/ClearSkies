package com.jHerscu.clearskies.data.model.response

import android.graphics.Bitmap
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IconResponse(
    val icon: Bitmap,
)
