package com.jHerscu.clearskies.data.source.local.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "local_icon")
data class LocalIcon(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "icon_code")
    val iconCode: String,
    @ColumnInfo(name = "icon_bitmap")
    val iconBitmap: Bitmap
)
