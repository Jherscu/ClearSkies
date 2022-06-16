package com.jHerscu.clearskies

import co.infinum.retromock.Retromock
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MockRetrofit {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit
        .Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val retromock: Retromock = Retromock
        .Builder()
        .retrofit(retrofit)
        .build()

    val service = retromock.create(FakeWeatherApiService::class.java)
}