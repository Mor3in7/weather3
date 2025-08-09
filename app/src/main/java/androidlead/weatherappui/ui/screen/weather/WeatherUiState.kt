package androidlead.weatherappui.ui.screen.weather


import androidlead.weatherappui.ui.domain.model.CurrentWeather
import androidlead.weatherappui.ui.domain.model.DailyForecast

data class WeatherUiState(
    val isLoading: Boolean = false,
    val currentWeather: CurrentWeather? = null,
    val dailyForecasts: List<DailyForecast> = emptyList(),
    val error: String? = null
)
