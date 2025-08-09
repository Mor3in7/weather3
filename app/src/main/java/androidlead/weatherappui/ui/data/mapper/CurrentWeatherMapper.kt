package androidlead.weatherappui.ui.data.mapper

import androidlead.weatherappui.ui.data.remote.dto.WeatherDto
import androidlead.weatherappui.ui.data.local.entity.CurrentWeatherEntity
import androidlead.weatherappui.ui.domain.model.CurrentWeather

// --- 1. WeatherDto → CurrentWeather (Domain)
fun WeatherDto.toCurrentWeather(): CurrentWeather {
    return CurrentWeather(
        cityName = location.name,
        temperature = current.tempC,
        feelslike_c = current.feelsLikeC,
        description = current.condition.text,
        iconUrl = "https:${current.condition.icon}",
        humidity = current.humidity.toFloat(),
        windSpeed = current.windKph,
        uvIndex = current.uv,
        o3 = current.airQuality?.o3,
        so2 = current.airQuality?.so2,
        co = current.airQuality?.co,
        lastUpdated = current.lastUpdated
    )
}

// --- 2. Domain → Entity
fun CurrentWeather.toCurrentWeatherEntity(): CurrentWeatherEntity {
    return CurrentWeatherEntity(
        id = 0,
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
        lastUpdated = lastUpdated,
        timestamp = System.currentTimeMillis()
    )
}

// --- 3. Entity → Domain
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
        lastUpdated = lastUpdated
    )
}
