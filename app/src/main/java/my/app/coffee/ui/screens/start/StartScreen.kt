package my.app.coffee.ui.screens.start

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import my.app.coffee.core.App
import my.app.coffee.core.navigation.Screen

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun StartScreen(navController: NavController) {
    val context = LocalContext.current
    val app = context.applicationContext as App
    val prefs = app.appComponent.provideSharedPreferences()

    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    // Запрашиваем разрешение независимо от авторизации
    LaunchedEffect(Unit) {
        if (!permissionState.status.isGranted) {
            permissionState.launchPermissionRequest()
        }
    }

    // Проверяем токен при запуске
    LaunchedEffect(Unit) {
        val token = prefs.getString("access_token", null)
        if (token.isNullOrBlank()) {
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Start.route) { inclusive = true }
            }
        } else {
            navController.navigate(Screen.CoffeeList.route) {
                popUpTo(Screen.Start.route) { inclusive = true }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}