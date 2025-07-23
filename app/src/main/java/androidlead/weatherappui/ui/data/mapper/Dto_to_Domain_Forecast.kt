package androidlead.weatherappui.ui.data.mapper


import androidlead.weatherappui.ui.data.remote.dto.WeatherDto
import androidlead.weatherappui.ui.domain.model.CurrentWeather
import androidlead.weatherappui.ui.domain.model.DailyForecast

// ------------------ Current Mapper --------------------

fun WeatherDto.toDailyForecast(): List<DailyForecast> {
    return forecast.forecastday.map { day ->
        DailyForecast(
            city = location.name,
            dayOfWeek = day.date, // بهتره تبدیل به نام روز بشه، بعداً می‌زنیم
            date = day.date,
            description = day.day.condition.icon,
            iconUrl = "https:${day.day.condition.icon}",
            maxTemp = day.day.maxTempC,
            minTemp = day.day.minTempC,
            dailyChanceOfRain = day.day.dailyChanceOfRain,
            pm25 = current.airQuality.pm25 , // چون forecast air_quality نداره، مقدار ثابته
            airQualityIndicatorColorHex = "#4CAF50",
        )
    }
}
