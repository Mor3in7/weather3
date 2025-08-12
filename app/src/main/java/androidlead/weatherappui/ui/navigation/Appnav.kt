package androidlead.weatherappui.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidlead.weatherappui.ui.screen.login.LoginScreen
import androidlead.weatherappui.ui.screen.signup.SignUp
import androidlead.weatherappui.ui.screen.home.HomeScreen
import androidlead.weatherappui.ui.screen.splash.Splash

@Composable
fun Appnav() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            Splash(
                onNavigateToLogin = {
                    navController.navigate("login") {
                        popUpTo("splash") { inclusive = true }
                    }
                },
                onNavigateToWeather = {
                    navController.navigate("home") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        composable("home") { HomeScreen() }

        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onSignUpClick = { navController.navigate("signup") }
            )
        }

        composable("signup") {
            SignUp(
                onSignUpSuccess = {
                    navController.navigate("home") {
                        popUpTo("signup") { inclusive = true }
                    }
                }
            )
        }
    }
}
