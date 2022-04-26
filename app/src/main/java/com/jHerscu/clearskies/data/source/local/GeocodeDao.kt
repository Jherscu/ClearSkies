package com.jHerscu.clearskies.data.source.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GeocodeDao {

    @Query("SELECT * FROM geocoded_city WHERE id = :id")
    fun getCityById(id: Int): Flow<GeocodedCity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: GeocodedCity)

    @Delete
    suspend fun deleteCity(city: GeocodedCity)

}