package androidlead.weatherappui.ui.domain.model

data class CurrentWeather(
    val cityName: String,
    val temperature: Float,
    val feelslike_c: Float,
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
