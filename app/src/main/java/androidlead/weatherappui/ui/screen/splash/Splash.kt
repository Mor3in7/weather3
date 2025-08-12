package androidlead.weatherappui.ui.screen.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Splash(
    onNavigateToLogin: () -> Unit,
    onNavigateToWeather: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    LaunchedEffect(isLoggedIn) {
        // We wait until isLoggedIn is not null to navigate.
        when (isLoggedIn) {
            true -> onNavigateToWeather()
            false -> onNavigateToLogin()
            null -> { /* Do nothing, wait for initial value */ }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
