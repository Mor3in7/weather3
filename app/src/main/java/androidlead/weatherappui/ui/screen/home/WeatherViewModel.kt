package androidlead.weatherappui.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidlead.weatherappui.ui.domain.model.CurrentWeather
import androidlead.weatherappui.ui.domain.model.DailyForecast
import androidlead.weatherappui.ui.domain.usecase.GetWeatherUseCase
import androidlead.weatherappui.ui.domain.util.Resource // Import the Result sealed class

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val weatherState: StateFlow<WeatherState> = _weatherState.asStateFlow()

    private val _selectedCity = MutableStateFlow("Tehran") // Default city
    val selectedCity: StateFlow<String> = _selectedCity.asStateFlow()

    init {
        fetchWeather(_selectedCity.value)
    }

    fun fetchWeather(city: String) {
        viewModelScope.launch {
            _selectedCity.value = city
            getWeatherUseCase(city).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _weatherState.value = WeatherState.Loading
                    }
                    is Result.Success -> {
                        result.data?.let { (currentWeather, dailyForecasts) ->
                            _weatherState.value = WeatherState.Success(currentWeather, dailyForecasts)
                        } ?: run {
                            _weatherState.value = WeatherState.Error("No data available.")
                        }
                    }
                    is Result.Error -> {
                        _weatherState.value = WeatherState.Error(result.message ?: "Unknown error")
                    }
                }
            }
        }
    }

    // Sealed class to represent the UI state of the weather screen
    sealed class WeatherState {
        object Loading : WeatherState()
        data class Success(val currentWeather: CurrentWeather, val dailyForecasts: List<DailyForecast>) : WeatherState()
        data class Error(val message: String) : WeatherState()
    }
}
