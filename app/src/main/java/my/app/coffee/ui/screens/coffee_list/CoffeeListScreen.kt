package my.app.coffee.ui.screens.coffee_list

import android.Manifest
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import my.app.coffee.core.App
import my.app.coffee.core.di.CoffeeListViewModelFactory
import my.app.coffee.core.navigation.Screen

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CoffeeListScreen(navController: NavController) {
    val context = LocalContext.current
    val app = context.applicationContext as App
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    val viewModel: CoffeeListViewModel = viewModel(
        factory = CoffeeListViewModelFactory(
            apiService = app.appComponent.apiService(),
            locationClient = app.appComponent.locationProvider()
        )
    )

    val locations by viewModel.locations
    val isLoading by viewModel.isLoading
    val error by viewModel.error
    val userLocation by viewModel.userLocation

    LaunchedEffect(Unit) {
        viewModel.loadLocations()
        permissionState.launchPermissionRequest()
    }

    LaunchedEffect(permissionState.status.isGranted) {
        if (permissionState.status.isGranted) {
            viewModel.loadUserLocation()
        }
        if(viewModel.error.value != null){
            navController.navigate(Screen.Login.route)
        }
    }

    Box(modifier = Modifier.fillMaxSize().padding(top = 16.dp)) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (!error.isNullOrEmpty()) {
            Text(
                text = "Ошибка: $error",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(locations) { place ->
                    val distanceText = viewModel.getDistanceTo(place)?.let {
                        String.format("Расстояние: $it")
                    }

                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFBF3EC)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable {
                                navController.navigate("menu/${place.id}")
                            },
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(
                                text = place.name,
                                style = MaterialTheme.typography.titleMedium,
                                color = Color(0xFF846340)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            if (distanceText != null) {
                                Text(
                                    text = distanceText,
                                    color = Color(0xFF846340).copy(alpha = 0.9f),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }

            Button(
                onClick = { navController.navigate(Screen.Map.route) },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(16.dp)
                    .navigationBarsPadding(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF846340),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("На карте")
            }
        }
    }
}