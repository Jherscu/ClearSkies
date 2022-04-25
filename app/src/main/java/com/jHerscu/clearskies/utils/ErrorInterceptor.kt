package com.jHerscu.clearskies.utils

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response = chain.proceed(request)
        when (response.code) {
            400 -> {
                // Bad request (missing or invalid params) - GeoCode
            }
            401 -> {
                // Unauthorized (invalid API key)
            }
            402 -> {
                // Payment required (organization disabled or usage exceeded)
            }
            403 -> {
                // Forbidden (insufficient permissions)
            }
            404 -> {
                // Bad request (missing or invalid params) - Weather
            }
            409 -> {
                // Conflict
            }
            429 -> {
                // Too many requests (rate limit exceeded, no state change, or selective throttling)
            }
            500 -> {
                // Internal server error
            }
            503 -> {
                // Service temporarily unavailable
            }
            in 100..299 -> {
                // Thumbs up!
            }
            in 300..399 -> {
                // Um.. Email the developer?..
            }
            else -> {
                // Woopsies!
            }
        }
        return response
    }
}