package com.jHerscu.clearskies.utils

typealias SimpleResource = Resource<Unit>

/**
 * Custom wrapper class to track success or failure of an executable task.
 * Uses [TextWrapper] to return either a dynamic string or a string resource id
 * to be consumed later.
 */
sealed class Resource<T>(val data: T? = null, val text: TextWrapper? = null) {
    class Success<T>(data: T?, text: TextWrapper? = null) : Resource<T>(data, text)
    class Error<T>(data: T? = null, text: TextWrapper) : Resource<T>(data, text)
}
