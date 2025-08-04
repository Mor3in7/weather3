package androidlead.weatherappui.ui.domain.repository

import kotlinx.coroutines.flow.Flow
import androidlead.weatherappui.ui.domain.model.CurrentWeather
import androidlead.weatherappui.ui.domain.model.DailyForecast
import androidlead.weatherappui.ui.domain.util.Resource

// Repository interface for weather operations
interface WeatherRepository {
    // Get current weather and 5-day forecast based on location
    fun getCurrentAndFiveDayForecast(location: String): Flow<Resource<Pair<CurrentWeather, List<DailyForecast>>>>
}
