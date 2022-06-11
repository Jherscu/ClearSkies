package com.jHerscu.clearskies.data.source.local

import androidx.room.*
import com.jHerscu.clearskies.data.source.local.entity.LocalDailyForecast
import com.jHerscu.clearskies.data.source.local.entity.LocalHourlyForecast
import com.jHerscu.clearskies.data.source.local.entity.LocalIcon
import com.jHerscu.clearskies.data.source.local.relation.DailyForecastAndIcon
import com.jHerscu.clearskies.data.source.local.relation.HourlyForecastAndIcon
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyForecast(forecast: LocalDailyForecast)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourlyForecast(forecast: LocalHourlyForecast)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocalIcon(icon: LocalIcon)

    @Delete
    suspend fun deleteDailyForecast(forecast: LocalDailyForecast)

    @Delete
    suspend fun deleteHourlyForecast(forecast: LocalHourlyForecast)

    /**
     * Checks if up to date weather forecasts exist for a given city. Must be received as Int and converted as Int.toBool()
     */
    @Query("SELECT CASE WHEN EXISTS (SELECT * FROM daily_forecast WHERE qualified_name = :qualifiedName AND time_in_millis = (:currentDate + 604800)) THEN 1 ELSE 0 END")
    fun validateDataUpToDateByCity(qualifiedName: String, currentDate: Int): Flow<Int>

    /**
     * Checks if weather forecasts exist for a given city. Must be received as Int and converted as Int.toBool()
     */
    @Query("SELECT CASE WHEN EXISTS (SELECT * FROM hourly_forecast WHERE qualified_name = :qualifiedName) THEN 1 ELSE 0 END")
    fun validateDataExistsByCity(qualifiedName: String): Flow<Int>

    /**
     * Updates list of hourly forecasts for each city whenever the data in the table changes.
     */
    @Transaction
    @Query("SELECT * FROM hourly_forecast WHERE qualified_name = :cityName ORDER BY time_in_millis ASC")
    fun getAllHourlyForecastsByCity(cityName: String): Flow<List<LocalHourlyForecast>>

    /**
     * Updates list of daily forecasts for each city whenever the data in the table changes.
     */
    @Transaction
    @Query("SELECT * FROM daily_forecast WHERE qualified_name = :cityName ORDER BY time_in_millis ASC")
    fun getAllDailyForecastsByCity(cityName: String): Flow<List<LocalDailyForecast>>

    /**
     * Updates icon for daily forecast whenever the data in the table changes.
     */
    @Transaction
    @Query("SELECT * FROM daily_forecast WHERE icon_code = :iconCode")
    suspend fun getDailyForecastAndIcon(iconCode: String): DailyForecastAndIcon

    /**
     * Updates icon for hourly forecast whenever the data in the table changes.
     */
    @Transaction
    @Query("SELECT * FROM hourly_forecast WHERE icon_code = :iconCode")
    suspend fun getHourlyForecastAndIcon(iconCode: String): HourlyForecastAndIcon

}