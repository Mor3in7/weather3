package androidlead.weatherappui.ui.data.mapper

import androidlead.weatherappui.ui.data.remote.dto.WeatherDto
import androidlead.weatherappui.ui.data.local.entity.DailyForecastEntity
import androidlead.weatherappui.ui.domain.model.DailyForecast
import java.text.SimpleDateFormat
import java.util.Locale

// --- 1. WeatherDto → List<DailyForecast> (Domain)
fun WeatherDto.toDailyForecasts(): List<DailyForecast> {
    return forecast.forecastday.map { day ->
        val date = day.date
        val parsedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)
        val dayOfWeek = if (parsedDate != null) {
            SimpleDateFormat("EEE", Locale.getDefault()).format(parsedDate)
        } else {
            "N/A" // یا هر مقدار پیش‌فرض که مناسب اپ شماست
        }
        val airQualityColor = when {
            current.airQuality?.pm25 ?: 0f < 12 -> "#4CAF50"   // خوب (سبز)
            current.airQuality?.pm25 ?: 0f < 35 -> "#FFC107"   // متوسط (زرد)
            else -> "#F44336"                                  // ناسالم (قرمز)
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
            pm25 = current.airQuality?.pm25 ?: 0f,
            airQualityIndicatorColorHex = airQualityColor
        )
    }
}

// --- 2. Domain → Entity
fun DailyForecast.toDailyForecastEntity(): DailyForecastEntity {
    return DailyForecastEntity(
        id = 0, // AutoGenerate
        city = city,
        dayOfWeek = dayOfWeek,
        date = date,
        maxTemp = maxTemp,
        minTemp = minTemp,
        avgTemp = (maxTemp + minTemp) / 2f,
        conditionText = description,
        iconUrl = iconUrl,
        dailyChanceOfRain = dailyChanceOfRain,
        airQualityIpm25 = pm25,
        airQualityIndicatorColorHex = airQualityIndicatorColorHex,
        timestamp = System.currentTimeMillis()
    )
}

// --- 3. Entity → Domain
fun DailyForecastEntity.toDailyForecast(): DailyForecast {
    return DailyForecast(
        city = city,
        dayOfWeek = dayOfWeek,
        date = date,
        description = conditionText,
        iconUrl = iconUrl,
        maxTemp = maxTemp,
        minTemp = minTemp,
        dailyChanceOfRain = dailyChanceOfRain,
        pm25 = airQualityIpm25,
        airQualityIndicatorColorHex = airQualityIndicatorColorHex
    )
}
