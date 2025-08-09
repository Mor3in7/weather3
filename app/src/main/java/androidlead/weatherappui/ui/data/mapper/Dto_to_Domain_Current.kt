//package androidlead.weatherappui.ui.data.mapper
//
//
//import androidlead.weatherappui.ui.data.remote.dto.WeatherDto
//import androidlead.weatherappui.ui.domain.model.CurrentWeather
//
//// ------------------ Current Mapper --------------------
//
//fun WeatherDto.toCurrentWeather(): CurrentWeather {
//    return CurrentWeather(
//        cityName = location.name,
//        temperature = current.tempC,
//        feelslike_c = current.feelsLikeC,
//        description = current.condition.text,
//        iconUrl = "https:${current.condition.icon}",
//        humidity = current.humidity.toFloat(),
//        windSpeed = current.windKph,
//        uvIndex = current.uv,
//        o3 = current.airQuality.o3,
//        so2 = current.airQuality.so2,
//        co = current.airQuality.co,
//        lastUpdated =  current.lastUpdated  )
//}