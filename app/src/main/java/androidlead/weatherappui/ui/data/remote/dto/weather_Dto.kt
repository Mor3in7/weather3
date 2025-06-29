package androidlead.weatherappui.ui.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CurrentWeatherDto(
    @SerializedName("city")
    val city: String,
    val temperature_current: String,
    val feels_like: String,
    val description: String,
    val day: String,
    val date: String,
    val icon_AQDT:String
    )
data class AirQualityDto(
    val Real_Feel:String,
    val wind: String,
    val uv_index: String,
    val rain: String,
    val o3: String,
    val so2: String,
    val icon_AQDT:String
)
data class weeklyforecast(
    val min:String,
    val max:String,
    val day: String,
    val date: String,
    val temperature: String,
    val description: String,
    val airquality: String,
    val icon_AQDT:String
)
