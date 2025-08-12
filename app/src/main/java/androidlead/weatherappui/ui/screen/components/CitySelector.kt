package androidlead.weatherappui.ui.screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidlead.weatherappui.ui.screen.home.WeatherViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CitySelector(
    viewModel: WeatherViewModel = hiltViewModel(),
    onCitySelected: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    var query by remember { mutableStateOf("") }
    val results by viewModel.citySearchResults.collectAsState()
    var debounceJob: Job? by remember { mutableStateOf(null) }

    Column(Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                debounceJob?.cancel()
                debounceJob = scope.launch {
                    delay(400)
                    if (query.length >= 2) {
                        viewModel.searchCities(query)
                    } else {
                        viewModel.clearCitySearchResults()
                    }
                }
            },
            label = { Text("Search city") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        LazyColumn {
            items(results) { city ->
                Text(
                    text = city,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onCitySelected(city)
                            viewModel.clearCitySearchResults()
                        }
                        .padding(12.dp)
                )
                Divider()
            }
        }
    }
}
