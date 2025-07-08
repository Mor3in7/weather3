package androidlead.weatherappui.ui.data.remote.dto

import com.google.gson.annotations.SerializedName

data class WeeklyForecastDto(
    @SerializedName("dt")
    val timestamp: Long,

    @SerializedName("temp")
    val temp: TempDto,

    @SerializedName("weather")
    val weather: List<WeatherDescriptionDto>,

    @SerializedName("air_quality_index") // اگر API داشته باشه
    val airQualityIndex: Int? = null // 1 تا 5
)
data class TempDto(
    val min: Double,
    val max: Double,
    val day: Double
)
