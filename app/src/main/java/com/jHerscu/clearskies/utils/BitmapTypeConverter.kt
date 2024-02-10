package com.jHerscu.clearskies.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

/**
 * Allows storage of bitmap weather icon,
 * and converts it to a BitmapDrawable when retrieved from the DB
 */
class BitmapTypeConverter {
    /**
     * Converts bitmap of loaded icon to byteArray for storage in Room DB.
     */
    @TypeConverter
    fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    /**
     * Converts byteArray into BitmapDrawable for easier use in displaying.
     */
    @TypeConverter // Convert to drawable afterwards for ease of use: BitmapDrawable(res, bitmap)
    fun byteArrayToBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}
