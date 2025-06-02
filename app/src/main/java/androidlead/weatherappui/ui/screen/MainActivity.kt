package androidlead.weatherappui.ui.screen

import android.os.Bundle
import androidlead.weatherappui.ui.screen.login.LOGin2
import androidlead.weatherappui.ui.screen.signup.SignUp
import androidlead.weatherappui.ui.theme.WeatherAppUiTheme
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.PopUpToBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppUiTheme {
                AppNavigator()
            }
        }   
    }
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LOGin2 {
                navController.navigate("signup")
            }
        }

        composable("signup") {
            SignUp {
                navController.navigate("weatherscreen")
                {
                    popUpTo("login") {
                        inclusive = true
                    }
                }
            }
        }
         composable("weatherscreen") {
            WeatherScreen()
        }


    }
}