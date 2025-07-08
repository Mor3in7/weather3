package androidlead.weatherappui.ui.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CurrentWeatherDto(
    @SerializedName("temp")
    val temperatureCurrent: Double,

    @SerializedName("feels_like")
    val feelsLike: Double,

    @SerializedName("uvi")
    val uvIndex: Double,

    @SerializedName("wind_speed")
    val windSpeed: Double,

    @SerializedName("dt")
    val timestamp: Long,

    @SerializedName("weather")
    val weather: List<WeatherDescriptionDto>,

    @SerializedName("rain")
    val rain: RainDto?
)
data class RainDto(
    @SerializedName("1h")
    val oneHour: Double
)