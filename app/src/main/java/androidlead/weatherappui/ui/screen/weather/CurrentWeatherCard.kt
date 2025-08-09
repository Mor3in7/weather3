package androidlead.weatherappui.ui.screen.weather

import androidlead.weatherappui.ui.domain.model.CurrentWeather
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CurrentWeatherCard(current: CurrentWeather) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("City: ${current.cityName}")
            Text("Temp: ${current.temperature}°C (Feels like ${current.feelslike_c}°C)")
            Text("Condition: ${current.description}")
            Text("Humidity: ${current.humidity}%")
            Text("Wind: ${current.windSpeed} km/h")
            Text("UV Index: ${current.uvIndex}")
        }
    }
}
