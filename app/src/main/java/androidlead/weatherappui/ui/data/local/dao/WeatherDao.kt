package androidlead.weatherappui.ui.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import androidlead.weatherappui.ui.data.local.entity.CurrentWeatherEntity
import androidlead.weatherappui.ui.data.local.entity.DailyForecastEntity
import androidx.room.Transaction

// اینترفیس DAO برای دسترسی به دیتابیس آب و هوا
@Dao
interface WeatherDao {

    // درج یا به‌روزرسانی آب و هوای فعلی
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(weather: CurrentWeatherEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyForecasts(forecasts: List<DailyForecastEntity>)

    @Query("SELECT * FROM CURRENT_WEATHER_TABLE WHERE city = :city")
    fun getCurrentWeather(city: String): Flow<CurrentWeatherEntity?>

    @Query("SELECT * FROM DAILY_FORECAST_TABLE WHERE city = :city ORDER BY date ASC")
    fun getDailyForecasts(city: String): Flow<List<DailyForecastEntity>>

    @Query("DELETE FROM CURRENT_WEATHER_TABLE WHERE city = :city")
    suspend fun deleteCurrentWeather(city: String)

    @Query("DELETE FROM DAILY_FORECAST_TABLE WHERE city = :city")
    suspend fun deleteDailyForecasts(city: String)

    @Transaction
    suspend fun saveWeatherData(currentWeather: CurrentWeatherEntity, dailyForecasts: List<DailyForecastEntity>) {
        deleteCurrentWeather(currentWeather.city) // Clear old data for the city
        deleteDailyForecasts(currentWeather.city) // Clear old data for the city
        insertCurrentWeather(currentWeather)
        insertDailyForecasts(dailyForecasts)
    }
}
