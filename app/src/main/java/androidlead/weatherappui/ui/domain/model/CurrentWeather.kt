package androidlead.weatherappui.ui.domain.model
// مدل دامین برای آب و هوای فعلی
data class CurrentWeather(
    val cityName: String,
    val temperature: Float,
    val feelslike_c: Float,
    val description: String,
    val iconUrl: String, // URL کامل آیکون
    val humidity: Float,
    val windSpeed: Float,
    val uvIndex: Float, // اضافه شدن UV Index
    val o3: Float?, // ازون
    val so2: Float?, // دی‌اکسید گوگرد
    val co: Float?, // مونوکسید کربن
    val lastUpdated: String
)

