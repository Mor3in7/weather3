package androidlead.weatherappui.ui.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDto(
    val current: CurrentDto,
    val forecast: ForecastDto,
    val location: LocationDto
)

@Serializable
data class LocationDto(
    val name: String,
    val region: String,
    val country: String
)

@Serializable
data class CurrentDto(
    @SerialName("temp_c")
    val tempC: Float,
    @SerialName("feelslike_c")
    val feelsLikeC: Float,
    val condition: ConditionDto,

    // for Air Quality
    @SerialName("wind_kph")
    val windKph: Float,
    val humidity: Int,
    val uv: Float,
    @SerialName("air_quality")
    val airQuality:     AirQualityDto,

    // theme switching
    @SerialName("is_day")
    val isDay: Int,

    @SerialName("last_updated")
    val lastUpdated: String

)

@Serializable
data class ConditionDto(
    val text: String,
    val icon: String
)

@Serializable
data class AirQualityDto(
    val o3: Float?,
    val so2: Float?,
    val co: Float?,
    val pm25: Float
)

@Serializable
data class ForecastDto(
    val forecastday: List<ForecastDayDto>
)

@Serializable
data class ForecastDayDto(
    val date: String,
    val day: DayDto
)

@Serializable
data class DayDto(
    @SerialName("maxtemp_c")
    val maxTempC: Float,
    @SerialName("mintemp_c")
    val minTempC: Float,
    @SerialName("daily_chance_of_rain")
    val dailyChanceOfRain: Float,
    val condition: ConditionFDto
)

@Serializable
data class ConditionFDto(
    val text: String,
    val icon: String
)
