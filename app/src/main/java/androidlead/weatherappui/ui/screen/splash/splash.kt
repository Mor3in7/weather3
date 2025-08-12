package androidlead.weatherappui.ui.screen.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidlead.weatherappui.ui.auth.SessionDataStore
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.first

@Composable
fun Splash(
    session: SessionDataStore,
    onNavigateToLogin: () -> Unit,
    onNavigateToWeather: () -> Unit
) {
    val isLoggedIn = session.isLoggedIn.collectAsState(initial = false).value
    val isInitialized = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val loggedIn = session.isLoggedIn.first()
        if (loggedIn) {
            onNavigateToWeather()
        } else {
            onNavigateToLogin()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
