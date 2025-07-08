package androidlead.weatherappui.ui.data.remote.dto

import com.google.gson.annotations.SerializedName

data class WeatherResponseDto(
    @SerializedName("current")
    val current: CurrentWeatherDto,

    @SerializedName("daily")
    val daily: List<WeeklyForecastDto>,

    @SerializedName("timezone")
    val city: String
)
