package my.app.coffee.ui.screens.coffee_list

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
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import my.app.coffee.core.App
import my.app.coffee.core.di.CoffeeListViewModelFactory


@Composable
fun CoffeeListScreen(navController: NavController) {
    val context = LocalContext.current
    val app = context.applicationContext as App
    val factory = CoffeeListViewModelFactory(app.appComponent.apiService())
    val viewModel: CoffeeListViewModel = viewModel(factory = factory)

    val locations = viewModel.locations

    val textColor = Color(0xFF846340)
    val cardColor = Color(0xFFFBF3EC)

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(locations) { place ->
                Card (
                    shape = RoundedCornerShape(12.dp),
                    backgroundColor = cardColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    elevation = 4.dp
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            text = place.name,
                            style = MaterialTheme.typography.h4,
                            color = textColor
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Координаты: ${place.point.latitude}, ${place.point.longitude}",
                            color = textColor.copy(alpha = 0.9f),
                            style = MaterialTheme.typography.h6
                        )
                    }
                }
            }
        }

        Button(
            onClick = { navController.navigate("map") },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
                .navigationBarsPadding()
        ) {
            Text("На карте")
        }
    }
}