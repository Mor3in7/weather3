package androidlead.weatherappui.ui

import androidlead.weatherappui.ui.screen.login.LOGin2
import android.os.Bundle
import androidlead.weatherappui.ui.navigation.Appnav
import androidlead.weatherappui.ui.screen.WeatherScreen
import androidlead.weatherappui.ui.screen.signup.SignUp
import androidlead.weatherappui.ui.theme.WeatherAppUiTheme
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppUiTheme {
            Appnav()
            }

            }
        }   
    }

