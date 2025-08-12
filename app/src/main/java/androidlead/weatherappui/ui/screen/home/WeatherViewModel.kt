package androidlead.weatherappui.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import androidlead.weatherappui.ui.domain.repository.WeatherRepository
import androidlead.weatherappui.ui.domain.util.Resource
import androidlead.weatherappui.ui.domain.model.CurrentWeather
import androidlead.weatherappui.ui.domain.model.DailyForecast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WeatherUiState(
    val isLoading: Boolean = false,
    val currentWeather: CurrentWeather? = null,
    val dailyForecasts: List<DailyForecast> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState = _uiState.asStateFlow()

    private val _citySearchResults = MutableStateFlow<List<String>>(emptyList())
    val citySearchResults = _citySearchResults.asStateFlow()

    init {
        loadWeatherData("Tehran")
    }

    fun loadWeatherData(city: String) {
        repository.getWeatherData(city).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _uiState.value = WeatherUiState(
                        isLoading = true,
                        currentWeather = result.data?.first,
                        dailyForecasts = result.data?.second ?: emptyList()
                    )
                }
                is Resource.Success -> {
                    _uiState.value = WeatherUiState(
                        currentWeather = result.data?.first,
                        dailyForecasts = result.data?.second ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _uiState.value = WeatherUiState(
                        error = result.message,
                        currentWeather = result.data?.first,
                        dailyForecasts = result.data?.second ?: emptyList()
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun searchCities(query: String) {
        viewModelScope.launch {
            _citySearchResults.value = repository.searchCities(query)
        }
    }

    fun clearCitySearchResults() {
        _citySearchResults.value = emptyList()
    }
}
