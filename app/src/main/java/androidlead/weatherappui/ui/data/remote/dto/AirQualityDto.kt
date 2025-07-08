package androidlead.weatherappui.ui.data.remote.dto

data class AirQualityDto(
    val realFeel: Double,
    val wind: Double,
    val uvIndex: Double,
    val rain: Double,
    val o3: Double,
    val so2: Double,
    val iconAQ: String
)
