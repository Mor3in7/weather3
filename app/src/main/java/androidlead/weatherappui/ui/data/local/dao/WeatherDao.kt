package androidlead.weatherappui.ui.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import androidlead.weatherappui.ui.data.local.entity.CurrentWeatherEntity
import androidlead.weatherappui.ui.data.local.entity.DailyForecastEntity

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(weather: CurrentWeatherEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyForecasts(forecasts: List<DailyForecastEntity>)

    @Query("SELECT * FROM current_weather_table WHERE city = :city LIMIT 1")
    fun getCurrentWeather(city: String): Flow<CurrentWeatherEntity?>

    @Query("SELECT * FROM daily_forecast_table WHERE city = :city ORDER BY date ASC")
    fun getDailyForecasts(city: String): Flow<List<DailyForecastEntity>>

    @Query("DELETE FROM current_weather_table WHERE city = :city")
    suspend fun deleteCurrentWeather(city: String)

    @Query("DELETE FROM daily_forecast_table WHERE city = :city")
    suspend fun deleteDailyForecasts(city: String)

    @Transaction
    suspend fun saveWeatherData(
        currentWeather: CurrentWeatherEntity,
        dailyForecasts: List<DailyForecastEntity>
    ) {
        deleteCurrentWeather(currentWeather.city)
        deleteDailyForecasts(currentWeather.city)
        insertCurrentWeather(currentWeather)
        insertDailyForecasts(dailyForecasts)
    }
}
