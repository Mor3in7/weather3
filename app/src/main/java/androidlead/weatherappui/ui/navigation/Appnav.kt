package androidlead.weatherappui.ui.navigation

import androidlead.weatherappui.ui.screen.WeatherScreen
import androidlead.weatherappui.ui.screen.login.LOGin2
import androidlead.weatherappui.ui.screen.signup.SignUp
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Appnav() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        //login to signUp
        composable("login") {
            LOGin2 (
                onLoginClick = {
                    navController.navigate("weatherscreen") {
                        popUpTo("login") { inclusive = true }
                    }
                               }
                ,
                onSignUpClick = {
                    navController.navigate("signup")
                }
            )
            }




        //sign Up to Home
        composable("signup") {
            SignUp(
                onSignUp_to_wescreen = {
                    navController.navigate("weatherscreen")
                    {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("weatherscreen") {
            WeatherScreen()
        }


    }
}