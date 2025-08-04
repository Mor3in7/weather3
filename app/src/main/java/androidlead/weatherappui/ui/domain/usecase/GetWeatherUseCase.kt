package androidlead.weatherappui.ui.domain.usecase

import kotlinx.coroutines.flow.Flow
import androidlead.weatherappui.ui.domain.model.CurrentWeather
import androidlead.weatherappui.ui.domain.model.DailyForecast
import androidlead.weatherappui.ui.domain.repository.WeatherRepository
import androidlead.weatherappui.ui.domain.util.Resource // Corrected import from Result to Resource
import javax.inject.Inject

// Use Case for fetching all weather information (current and forecast)
class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    // Operator function allows calling the use case like a function (e.g., getWeatherUseCase("Tehran"))
    operator fun invoke(location: String): Flow<Resource<Pair<CurrentWeather, List<DailyForecast>>>> {
        // Corrected to call the appropriate repository method
        return repository.getCurrentAndFiveDayForecast(location)
    }
}
