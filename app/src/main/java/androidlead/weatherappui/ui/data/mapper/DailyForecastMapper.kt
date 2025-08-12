package androidlead.weatherappui.ui.data.mapper

import androidlead.weatherappui.ui.data.remote.dto.WeatherDto
import androidlead.weatherappui.ui.data.local.entity.DailyForecastEntity
import androidlead.weatherappui.ui.domain.model.DailyForecast
import java.text.SimpleDateFormat
import java.util.Locale

fun WeatherDto.toDailyForecasts(): List<DailyForecast> {
    return forecast.forecastday.map { day ->
        val date = day.date
        val parsedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)
        val dayOfWeek = parsedDate?.let {
            SimpleDateFormat("EEE", Locale.getDefault()).format(it)
        } ?: "N/A"

        val pm25 = day.day.airQuality?.pm25 ?: 0f
        val airQualityColor = when {
            pm25 < 12 -> "#4CAF50"
            pm25 < 35 -> "#FFC107"
            else -> "#F44336"
        }

        DailyForecast(
            city = location.name,
            dayOfWeek = dayOfWeek,
            date = date,
            description = day.day.condition.text,
            iconUrl = "https:${day.day.condition.icon}",
            maxTemp = day.day.maxTempC,
            minTemp = day.day.minTempC,
            dailyChanceOfRain = day.day.dailyChanceOfRain,
            pm25 = pm25,
            airQualityIndicatorColorHex = airQualityColor,
            avgTemp = day.day.avgTempC
        )
    }
}

fun DailyForecast.toDailyForecastEntity(): DailyForecastEntity {
    return DailyForecastEntity(
        city = city,
        dayOfWeek = dayOfWeek,
        date = date,
        maxTemp = maxTemp,
        minTemp = minTemp,
        avgTemp = avgTemp,
        conditionText = description,
        iconUrl = iconUrl,
        dailyChanceOfRain = dailyChanceOfRain,
        airQualityPm25 = pm25,
        airQualityIndicatorColorHex = airQualityIndicatorColorHex,
        timestamp = System.currentTimeMillis()
    )
}

fun DailyForecastEntity.toDailyForecast(): DailyForecast {
    return DailyForecast(
        city = city,
        dayOfWeek = dayOfWeek,
        date = date,
        description = conditionText,
        iconUrl = iconUrl,
        maxTemp = maxTemp,
        minTemp = minTemp,
        avgTemp = avgTemp,
        dailyChanceOfRain = dailyChanceOfRain,
        pm25 = airQualityPm25,
        airQualityIndicatorColorHex = airQualityIndicatorColorHex
    )
}
