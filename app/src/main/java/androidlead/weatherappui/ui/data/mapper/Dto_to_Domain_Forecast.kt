//package androidlead.weatherappui.ui.data.mapper
//
//
//import androidlead.weatherappui.ui.data.remote.dto.WeatherDto
//import androidlead.weatherappui.ui.domain.model.CurrentWeather
//import androidlead.weatherappui.ui.domain.model.DailyForecast
//
//// ------------------ Current Mapper --------------------
//
//
//import androidlead.weatherappui.ui.data.local.entity.DailyForecastEntity
//import java.text.SimpleDateFormat
//import java.util.Locale
//
//// Map from WeatherDto to a list of DailyForecast domain models
//fun WeatherDto.toDailyForecasts(): List<DailyForecast> {
//    return forecast.forecastday.map { day ->
//        val date = day.date // e.g., "2023-07-23"
//        val parsedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)
//        val dayOfWeek = SimpleDateFormat("EEE", Locale.getDefault()).format(parsedDate) // e.g., "Mon"
//
//        // Determine air quality color based on pm25 value (example logic)
//        val airQualityColor = when {
//            current.airQuality.pm25 < 12 -> "#4CAF50" // Good (Green)
//            current.airQuality.pm25 < 35 -> "#FFC107" // Moderate (Yellow)
//            else -> "#F44336" // Unhealthy (Red)
//        }
//
//        DailyForecast(
//            city = location.name,
//            dayOfWeek = dayOfWeek,
//            date = date,
//            description = day.day.condition.text, // Use text for description
//            iconUrl = "https:${day.day.condition.icon}",
//            maxTemp = day.day.maxTempC,
//            minTemp = day.day.minTempC,
//            dailyChanceOfRain = day.day.dailyChanceOfRain,
//            pm25 = current.airQuality.pm25, // Using current air quality as forecast doesn't have it
//            airQualityIndicatorColorHex = airQualityColor,
//        )
//    }
//}