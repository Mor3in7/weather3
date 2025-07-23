// androidlead.weatherappui.ui.data.local.entity/CurrentWeather.kt
package androidlead.weatherappui.ui.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// انتیتی Room برای ذخیره آب و هوای فعلی
@Entity(tableName = "current_weather_table")
data class CurrentWeatherEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
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
    val lastUpdated: String
)
