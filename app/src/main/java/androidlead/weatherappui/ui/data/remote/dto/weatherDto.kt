package androidlead.weatherappui.ui.data.remote.dto

//import androidx.room.Index
//
//data class weatherDto(
//val Currentweather:Current,
//val AirQuality:AirQuality,
//    val weeklyForecast: List<ForecastDay>
//)
//data class Current(
//    val feelslike_c: Float,
//    val temp_c: Float,
//    val condition: Condition,
////for Air Quality
//    val humidity: Float,
//    val wind_kph: Float,
//    val Uv:Float,
//)
//data class Condition(
//    val text: String,
//    val icon: String,
//    val code: Int
//)
//// ----------------------- Air Quality ------------------------
//data class AirQuality(
//    val o3: Float?,         // Ozone
//    val so2: Float?,        // Sulphur Dioxide
//    val uv_wind_humidity:Current// Ultraviolet
//)
//// ----------------------- Forecast ------------------------
//data class ForecastDay(
//    val date: String,
//    val day: Day
//)
//
//data class Day(
//    val maxtemp_c: Float,
//    val mintemp_c: Float,
//    val avgtemp_c: Float,
//    val daily_chance_of_rain: Int,
//    val condition: Condition
//)
//
//data class WeatherDto(
//    val Currentweather:Current,
//    val AirQuality:AirQuality,
//    val weeklyForecast: List<ForecastDay>
//        )
//////////////
data class WeatherDto(
    val current: Current,
    val forecast: Forecast
)

data class Current(
    val temp_c: Float,
    val feelslike_c: Float,
    val humidity: Float,
    val wind_kph: Float,
    val uv: Float,
    val condition: Condition,
    val air_quality: AirQuality
)

data class Condition(
    val text: String,
    val icon: String,
    val code: Int
)

data class AirQuality(
    val o3: Float?,
    val so2: Float?,
    val co: Float?
)

// ----------------------- Forecast ------------------------

data class Forecast(
    val forecastday: List<ForecastDay>
)

data class ForecastDay(
    val date: String,
    val day: Day
)

data class Day(
    val maxtemp_c: Float,
    val mintemp_c: Float,
    val avgtemp_c: Float,
    val daily_chance_of_rain: Float,
    val condition: Condition
)
