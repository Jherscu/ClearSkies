package com.jHerscu.clearskies.data.source.local

import androidx.room.*
import com.jHerscu.clearskies.data.source.local.entity.LocalDailyForecast
import com.jHerscu.clearskies.data.source.local.entity.LocalHourlyForecast
import com.jHerscu.clearskies.data.source.local.entity.LocalIcon
import com.jHerscu.clearskies.data.source.local.relation.DailyForecastAndIcon
import com.jHerscu.clearskies.data.source.local.relation.HourlyForecastAndIcon
import kotlinx.coroutines.flow.Flow

const val ICON_QUERY: String = "SELECT * FROM local_icon WHERE icon_code = :iconCode"

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

    @Delete
    suspend fun deleteLocalIcon(icon: LocalIcon)

    /**
     * Updates list of hourly forecasts for each city whenever the data in the table changes.
     */
    @Transaction
    @Query("SELECT * FROM hourly_forecast WHERE qualified_name = :cityName ORDER BY hour_in_mill ASC")
    fun getCityWithHourlyForecastsByCity(cityName: String): Flow<List<LocalHourlyForecast>>

    /**
     * Updates list of daily forecasts for each city whenever the data in the table changes.
     */
    @Transaction
    @Query("SELECT * FROM daily_forecast WHERE qualified_name = :cityName ORDER BY date_in_mill ASC")
    fun getCityWithDailyForecastsByCity(cityName: String): Flow<List<LocalDailyForecast>>

    /**
     * Updates icon for daily forecast whenever the data in the table changes.
     */
    @Transaction
    @Query(ICON_QUERY)
    fun getDailyForecastAndIcon(iconCode: String): Flow<DailyForecastAndIcon>

    /**
     * Updates icon for hourly forecast whenever the data in the table changes.
     */
    @Transaction
    @Query(ICON_QUERY)
    fun getHourlyForecastAndIcon(iconCode: String): Flow<HourlyForecastAndIcon>

}