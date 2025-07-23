package androidlead.weatherappui.ui.data.mapper


import androidlead.weatherappui.ui.data.local.entity.DailyForecastEntity
import androidlead.weatherappui.ui.domain.model.DailyForecast


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
        airQualityIndicatorColorHex = airQualityIndicatorColorHex,)
}

fun DailyForecast.toDailyForecastEntity(): DailyForecastEntity {
    return DailyForecastEntity(
        city = city,
        dayOfWeek = dayOfWeek,
        date = date,
        conditionText = description,
        iconUrl = iconUrl,
        maxTemp = maxTemp,
        minTemp = minTemp,
        dailyChanceOfRain = dailyChanceOfRain,
        airQualityIpm25 = pm25,
        airQualityIndicatorColorHex = airQualityIndicatorColorHex,
    )
}