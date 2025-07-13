package androidlead.weatherappui.ui.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import androidlead.weatherappui.ui.data.local.entity.CurrentWeatherEntity
import androidlead.weatherappui.ui.data.local.entity.DailyForecastEntity

// اینترفیس DAO برای دسترسی به دیتابیس آب و هوا
@Dao
interface WeatherDao {

    // درج یا به‌روزرسانی آب و هوای فعلی
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(weather: CurrentWeatherEntity)

    // دریافت آب و هوای فعلی
    @Query("SELECT * FROM current_weather_table LIMIT 1")
    fun getCurrentWeather(): Flow<CurrentWeatherEntity?>

    // حذف آب و هوای فعلی
    @Query("DELETE FROM current_weather_table")
    suspend fun deleteCurrentWeather()

    // درج یا به‌روزرسانی لیست پیش‌بینی روزانه
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyForecasts(forecasts: List<DailyForecastEntity>)

    // دریافت لیست پیش‌بینی روزانه
    @Query("SELECT * FROM daily_forecast_table ORDER BY dateEpochMillis ASC")
    fun getDailyForecasts(): Flow<List<DailyForecastEntity>>

    // حذف تمام پیش‌بینی‌های روزانه
    @Query("DELETE FROM daily_forecast_table")
    suspend fun deleteDailyForecasts()
}
