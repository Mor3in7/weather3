package androidlead.weatherappui.ui.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "daily_forecast_table",
    indices = [Index(value = ["city", "date"], unique = false)]
)
data class DailyForecastEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val city: String,
    val dayOfWeek: String,
    val date: String,
    val maxTemp: Float,
    val minTemp: Float,
    val avgTemp: Float,
    val conditionText: String,
    val iconUrl: String,
    val dailyChanceOfRain: Float,
    val airQualityPm25: Float,
    val airQualityIndicatorColorHex: String,
    val timestamp: Long
)
