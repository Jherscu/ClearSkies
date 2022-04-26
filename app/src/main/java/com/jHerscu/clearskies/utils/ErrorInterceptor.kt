package com.jHerscu.clearskies.utils

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber

class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response = chain.proceed(request)
        when (response.code) {
            400 -> {
                Timber.e("Bad request (missing or invalid params) - GeoCode\n${response.code}: ${response.message}")
            }
            401 -> {
                Timber.e("Unauthorized (invalid API key)\n${response.code}: ${response.message}")
            }
            402 -> {
                Timber.e("Payment required (organization disabled or usage exceeded)\n${response.code}: ${response.message}")
            }
            403 -> {
                Timber.e("Forbidden (insufficient permissions)\n${response.code}: ${response.message}")
            }
            404 -> {
                Timber.e("Bad request (missing or invalid params) - Weather\n${response.code}: ${response.message}")
            }
            409 -> {
                Timber.e("Conflict\n${response.code}: ${response.message}")
            }
            429 -> {
                Timber.e("Too many requests (rate limit exceeded, no state change, or selective throttling)\n${response.code}: ${response.message}")
            }
            500 -> {
                Timber.e("Internal server error\n${response.code}: ${response.message}")
            }
            503 -> {
                Timber.e("Service temporarily unavailable\n${response.code}: ${response.message}")
            }
            in 100..299 -> {
                Timber.i("Thumbs up!\n${response.code}: ${response.message}")
            }
            in 300..399 -> {
                Timber.e("Redirects and such.\n${response.code}: ${response.message}")
            }
            else -> {
                Timber.e("Woopsies!\n${response.code}: ${response.message}")
                TODO("Figure out how to handle various codes")
            }
        }
        return response
    }
}