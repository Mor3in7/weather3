package androidlead.weatherappui.ui.screen.weather


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidlead.weatherappui.ui.domain.model.DailyForecast

@Composable
fun DailyForecastItem(forecast: DailyForecast) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(forecast.dayOfWeek)
                Text(forecast.date)
                Text(forecast.description)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("Max: ${forecast.maxTemp}°")
                Text("Min: ${forecast.minTemp}°")
                Text("Rain chance: ${forecast.dailyChanceOfRain}%")
            }
        }
    }
}
