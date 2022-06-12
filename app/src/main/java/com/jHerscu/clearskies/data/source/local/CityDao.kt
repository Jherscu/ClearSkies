package com.jHerscu.clearskies.data.source.local

import androidx.room.*
import com.jHerscu.clearskies.data.source.local.entity.LocalGeocodedCity
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {

    @Query("SELECT * FROM geocoded_city")
    fun getAllCities(): Flow<List<LocalGeocodedCity>>

    @Query("SELECT * FROM geocoded_city WHERE qualified_name = :name")
    suspend fun getCityByName(name: String): LocalGeocodedCity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: LocalGeocodedCity)

    @Delete
    suspend fun deleteCity(city: LocalGeocodedCity)

}