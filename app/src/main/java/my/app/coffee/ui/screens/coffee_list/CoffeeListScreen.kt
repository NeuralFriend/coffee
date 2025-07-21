package my.app.coffee.ui.screens.coffee_list

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
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import my.app.coffee.core.App
import my.app.coffee.core.di.CoffeeListViewModelFactory
import my.app.coffee.ui.navigation.Screen


@Composable
fun CoffeeListScreen(navController: NavController) {
    val context = LocalContext.current
    val app = context.applicationContext as App
    val factory = CoffeeListViewModelFactory(app.appComponent.apiService())
    val viewModel: CoffeeListViewModel = viewModel(factory = factory)

    val locations by viewModel.locations

    val textColor = Color(0xFF846340)
    val cardColor = Color(0xFFFBF3EC)
    val buttonColor = Color(0xFF846340)


    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(locations) { place ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    backgroundColor = cardColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable {
                            navController.navigate("menu/${place.id}")
                        },
                    elevation = 4.dp
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            text = place.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = textColor
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Координаты: ${place.point.latitude}, ${place.point.longitude}",
                            color = textColor.copy(alpha = 0.9f),
                            style = MaterialTheme.typography.bodySmall
                        )
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
                containerColor = buttonColor,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("На карте")
        }
    }
}