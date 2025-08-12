package androidlead.weatherappui.ui.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather_table")
data class CurrentWeatherEntity(
    @PrimaryKey
    val city: String,
    val temperature: Float,
    val feelsLike: Float,
    val description: String,
    val iconUrl: String,
    val humidity: Float,
    val windSpeed: Float,
    val uvIndex: Float,
    val o3: Float?,
    val so2: Float?,
    val co: Float?,
    val lastUpdated: String,
    val timestamp: Long
)
