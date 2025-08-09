package androidlead.weatherappui.ui.screen.weather

import androidlead.weatherappui.ui.theme.ColorBackground
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // شهر انتخابی پیش‌فرض (می‌تونه بعداً از CitySelector بیاد)
    val city = remember { mutableStateOf("Tehran") }

    LaunchedEffect(city.value) {
        viewModel.loadWeather(city.value)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ColorBackground
    ) { paddings ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddings)
                .padding(horizontal = 24.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                return@Column
            }

            uiState.error?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                return@Column
            }

            // ===== UI Sections =====

            Text(
                text = "Weather for ${city.value}",
                style = MaterialTheme.typography.headlineSmall
            )

            uiState.currentWeather?.let {
                CurrentWeatherCard(current = it)
            }

            Text("Daily Forecast")
            uiState.dailyForecasts.forEach {
                DailyForecastItem(forecast = it)
            }
        }
    }
}
