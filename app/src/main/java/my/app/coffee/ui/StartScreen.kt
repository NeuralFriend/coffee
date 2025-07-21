package my.app.coffee.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import my.app.coffee.core.App
import my.app.coffee.ui.navigation.Screen

@Composable
fun StartScreen(navController: NavController) {
    val context = LocalContext.current
    val app = context.applicationContext as App
    val prefs = app.appComponent.provideSharedPreferences()

    LaunchedEffect(Unit) {
        val token = prefs.getString("access_token", null)
        if (token.isNullOrBlank()) {
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Start.route) { inclusive = true }
            }
        }
        else {
            navController.navigate(Screen.CoffeeList.route) {
                popUpTo(Screen.Start.route) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}