package androidlead.weatherappui.ui.data.remote.dto

import com.google.gson.annotations.SerializedName

//data class CurrentWeatherDto(
//    @SerializedName("timezone")
//    val city: String,
//    @SerializedName("current.temp")
//    val temperature_current: String,
//    @SerializedName("current.feels_like")
//    val feels_like: String,
//    @SerializedName("current.weather.description")
//    val description: String,
//    @SerializedName("dt")
//    val day: String,
//    @SerializedName("date")
//    val date: String,
//    val icon_AQDT:String
//    )
data class WeatherResponseDto(
    @SerializedName("timezone")
    val city: String,

    @SerializedName("current")
    val current: CurrentWeatherDto
)

data class CurrentWeatherDto(
    @SerializedName("temp")
    val temperatureCurrent: Double,

    @SerializedName("feels_like")
    val feelsLike: Double,

    @SerializedName("dt")
    val timestamp: Long,

    @SerializedName("weather")
    val weather: List<WeatherDescriptionDto>
)

data class WeatherDescriptionDto(
    val description: String,
    val icon: String
)
data class AirQualityDto(
    val Real_Feel: Int,         // از main.aqi
    val wind: Double,           // از current.wind_speed
    val uv_index: Double,       // از current.uvi
    val rain: Double,           // از current.rain?.oneHour ?: 0.0
    val o3: Double,             // از components.o3
    val so2: Double,            // از components.so2
    val icon_AQDT: String       // آیکن برای نمایش (مثلاً weather[0].icon)
)
data class weeklyforecast(
    val min: Double,            // temp.min
    val max: Double,            // temp.max
    val day: String,            // تبدیل‌شده از dt به روز هفته (مثلاً Monday)
    val date: String,           // تبدیل‌شده از dt به تاریخ (مثلاً 2025-06-30)
    val temperature: Double,    // temp.day
    val description: String,    // weather[0].description
    val airquality: Int,        // از aqi (عدد از 1 تا 5)
    val icon_AQDT: String       // weather[0].iconssssss
)

