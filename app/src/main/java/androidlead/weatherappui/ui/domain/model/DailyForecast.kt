package androidlead.weatherappui.ui.domain.model

data class DailyForecast(
    val city: String,
    val dayOfWeek: String,
    val date: String,
    val description: String,
    val iconUrl: String,
    val maxTemp: Float,
    val minTemp: Float,
    val avgTemp: Float,
    val dailyChanceOfRain: Float,
    val pm25: Float,
    val airQualityIndicatorColorHex: String,
)
