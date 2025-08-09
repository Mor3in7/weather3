package androidlead.weatherappui.ui

import android.os.Bundle
import androidlead.weatherappui.ui.navigation.Appnav
import androidlead.weatherappui.ui.theme.WeatherAppUiTheme
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppUiTheme {
            Appnav()
            }

            }
        }   
    }

