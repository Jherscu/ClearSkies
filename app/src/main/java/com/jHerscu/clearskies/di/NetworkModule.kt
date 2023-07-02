package com.jHerscu.clearskies.di

import android.content.Context
import coil.ImageLoader
import com.jHerscu.clearskies.BuildConfig
import com.jHerscu.clearskies.data.source.remote.GeocodeApiService
import com.jHerscu.clearskies.data.source.remote.WeatherApiService
import com.jHerscu.clearskies.utils.ErrorInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

private const val BASE_WEATHER_URL = "https://api.openweathermap.org/"

private const val BASE_GEOCODE_URL = "https://api.radar.io/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideGeocodeHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val builder = chain.request().newBuilder()
            builder.addHeader("Authorization", BuildConfig.RADAR_GEOCODE_KEY)
            return@Interceptor chain.proceed(builder.build())
        }
    }

    @Singleton
    @Provides
    fun provideErrorInterceptor(): ErrorInterceptor {
        return ErrorInterceptor()
    }

    /**
     * Creates base version of HttpClient to share between both retrofit instances
     */
    @Singleton
    @Provides
    @Named("base_client")
    fun provideBaseHttpClient(errorInterceptor: ErrorInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(errorInterceptor)
            readTimeout(15, TimeUnit.SECONDS)
            connectTimeout(15, TimeUnit.SECONDS)
        }.build()
    }

    /**
     * Creates modified "shallow" version of HttpClient to add header to geocode calls
     */
    @Singleton
    @Provides
    @Named("header_client")
    fun provideHttpClientWithGeocodeHeader(
        @Named("base_client") baseHttpClient: OkHttpClient,
        headerInterceptor: Interceptor
    ): OkHttpClient {
        return baseHttpClient
            .newBuilder()
            .addInterceptor(headerInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideCoil(@ApplicationContext context: Context): ImageLoader {
        return ImageLoader(context)
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    @Named("weather_retrofit")
    fun provideWeatherRetrofit(
        moshi: Moshi,
        @Named("base_client") baseClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_WEATHER_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(baseClient)
            .build()
    }

    @Singleton
    @Provides
    @Named("geocode_retrofit")
    fun provideGeocodeRetrofit(
        moshi: Moshi,
        @Named("header_client") headerClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_GEOCODE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(headerClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherApiService(@Named("weather_retrofit") weatherRetrofit: Retrofit): WeatherApiService {
        return weatherRetrofit.create(WeatherApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideGeocodeApiService(@Named("geocode_retrofit") geocodeRetrofit: Retrofit): GeocodeApiService {
        return geocodeRetrofit.create((GeocodeApiService::class.java))
    }
}
