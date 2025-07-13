// androidlead.weatherappui.ui.data.local.entity/CurrentWeather.kt
package androidlead.weatherappui.ui.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// انتیتی Room برای ذخیره آب و هوای فعلی
@Entity(tableName = "current_weather_table")
data class CurrentWeatherEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0, // یک ID ثابت برای اطمینان از اینکه فقط یک رکورد آب و هوای فعلی وجود دارد
    val cityName: String,
    val temperature: Float, // تغییر به Float برای مطابقت با DTO
    val feelsLike: Float, // تغییر به Float
    val description: String,
    val icon: String,
    val humidity: Float, // تغییر به Float
    val windSpeed: Float, // تغییر به Float
    val uvIndex: Float, // اضافه شدن UV Index
    val o3: Float?, // اضافه شدن O3
    val so2: Float?, // اضافه شدن SO2
    val co: Float?, // اضافه شدن CO
    val timestamp: Long // زمان ذخیره سازی برای اعتبارسنجی تازگی داده
)
