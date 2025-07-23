package androidlead.weatherappui.ui.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// انتیتی Room برای ذخیره پیش‌بینی روزانه
@Entity(tableName = "daily_forecast_table")
data class DailyForecastEntity(
    @PrimaryKey(autoGenerate = true) // Auto-generate ID for each forecast entry
    val id: Int = 0,
    val city: String, // Foreign key to link with CurrentWeatherEntity
    val dayOfWeek: String,
    val date: String,
    val maxTemp: Float,
    val minTemp: Float,
    val conditionText: String,
    val iconUrl: String,
    val dailyChanceOfRain: Float,
    val airQualityIpm25: Float,
    val airQualityIndicatorColorHex: String,
)

