package androidlead.weatherappui.ui.domain.repository

import androidlead.weatherappui.ui.domain.model.CurrentWeather
import androidlead.weatherappui.ui.domain.model.DailyForecast
import androidlead.weatherappui.ui.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    /**
     * دریافت وضعیت فعلی و پیش‌بینی چندروزه برای یک شهر.
     */
    fun getWeatherData(
        location: String
    ): Flow<Resource<Pair<CurrentWeather, List<DailyForecast>>>>
}
