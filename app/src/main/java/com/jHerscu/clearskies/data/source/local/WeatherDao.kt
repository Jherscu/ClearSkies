package com.jHerscu.clearskies.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.jHerscu.clearskies.data.source.local.entity.LocalDailyForecast
import com.jHerscu.clearskies.data.source.local.entity.LocalHourlyForecast
import com.jHerscu.clearskies.data.source.local.entity.LocalIcon
import com.jHerscu.clearskies.data.source.local.relation.DailyForecastAndIcon
import com.jHerscu.clearskies.data.source.local.relation.HourlyForecastAndIcon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

private const val WEEK_IN_SECONDS = 604800 // TODO(jherscu): convert to milliseconds when responses are converted

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertDailyForecast(forecast: LocalDailyForecast)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertHourlyForecast(forecast: LocalHourlyForecast)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertLocalIcon(icon: LocalIcon)

    @Delete
    suspend fun deleteDailyForecast(forecast: LocalDailyForecast)

    @Delete
    suspend fun deleteHourlyForecast(forecast: LocalHourlyForecast)

    /**
     * Checks if up to date weather forecasts exist for a given city. Checks if daily weather is available 7 days out.
     */
    @Query("SELECT * FROM daily_forecast WHERE qualified_name = :qualifiedName AND time_in_millis = (:currentDate + $WEEK_IN_SECONDS)")
    fun validateDataUpToDateByCity(
        qualifiedName: String,
        currentDate: Int,
    ): Flow<LocalDailyForecast?>

    fun upToDateCityInfoExists(
        qualifiedName: String,
        currentDate: Int,
    ): Flow<Boolean> =
        validateDataUpToDateByCity(qualifiedName, currentDate)
            .distinctUntilChanged()
            .map { it != null }

    /**
     * Checks if weather forecasts exist for a given city.
     */
    @Query("SELECT * FROM hourly_forecast WHERE qualified_name = :qualifiedName")
    fun validateDataExistsByCity(qualifiedName: String): Flow<List<LocalHourlyForecast>?>

    fun cityDataExists(qualifiedName: String): Flow<Boolean> =
        validateDataExistsByCity(qualifiedName)
            .distinctUntilChanged()
            .map { !it.isNullOrEmpty() }

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
