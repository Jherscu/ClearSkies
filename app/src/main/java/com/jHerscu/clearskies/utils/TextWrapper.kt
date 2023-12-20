package com.jHerscu.clearskies.utils

import android.content.Context
import androidx.annotation.StringRes

/**
 * Custom wrapper class which returns either a dynamic string
 * or a string resource id to be consumed later.
 */
sealed class TextWrapper { // TODO(jherscu): verify need
    data class DynamicString(val string: String) : TextWrapper()

    class StringResource(
        @StringRes val id: Int,
        vararg val args: Any,
    ) : TextWrapper()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> string
            is StringResource -> context.getString(id, *args)
        }
    }
}
