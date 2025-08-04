package androidlead.weatherappui.ui.data.mapper

import androidlead.weatherappui.ui.data.local.entity.DailyForecastEntity
import androidlead.weatherappui.ui.domain.model.DailyForecast

/// CurrentWeatherEntityMapper.kt
import androidlead.weatherappui.ui.data.local.entity.CurrentWeatherEntity
import androidlead.weatherappui.ui.domain.model.CurrentWeather

// مپ کردن از Entity دیتابیس به مدل دامنه
fun CurrentWeatherEntity.toCurrentWeather(): CurrentWeather {
    return CurrentWeather(
        cityName = city,
        temperature = temperature,
        feelslike_c = feelsLike,
        description = description,
        iconUrl = iconUrl,
        humidity = humidity,
        windSpeed = windSpeed,
        uvIndex = uvIndex,
        o3 = o3,
        so2 = so2,
        co = co,
        lastUpdated=lastUpdated
        )
}

// مپ کردن از مدل دامنه به Entity برای ذخیره در دیتابیس
fun CurrentWeather.toCurrentWeatherEntity(): CurrentWeatherEntity {
    return CurrentWeatherEntity(
        city = cityName,
        temperature = temperature,
        feelsLike = feelslike_c,
        description = description,
        iconUrl = iconUrl,
        humidity = humidity,
        windSpeed = windSpeed,
        uvIndex = uvIndex,
        o3 = o3,
        so2 = so2,
        co = co,
        id = 0,
        lastUpdated = lastUpdated,
        timestamp = System.currentTimeMillis() // Set current timestamp
    )
}
