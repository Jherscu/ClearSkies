package com.jHerscu.clearskies.data.source.local

import androidx.room.*
import com.jHerscu.clearskies.data.source.local.entity.GeocodedCity
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {

    @Query("SELECT * FROM geocoded_city WHERE qualified_name = :name")
    fun getCityByName(name: String): Flow<GeocodedCity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: GeocodedCity)

    @Delete
    suspend fun deleteCity(city: GeocodedCity)

}