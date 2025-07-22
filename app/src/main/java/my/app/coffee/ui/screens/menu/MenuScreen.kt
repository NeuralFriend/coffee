package my.app.coffee.ui.screens.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import my.app.coffee.R
import my.app.coffee.core.App
import my.app.coffee.core.CartManager
import my.app.coffee.core.di.MenuViewModelFactory
import my.app.coffee.core.navigation.Screen
import my.app.coffee.ui.theme.buttonColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavController, locationId: Int) {
    val context = LocalContext.current
    val app = context.applicationContext as App

    val factory = MenuViewModelFactory(
        apiService = app.appComponent.apiService(),
        locationId = locationId
    )
    val viewModel: MenuViewModel = viewModel(factory = factory)

    val menuItems by viewModel.menu
    val errorMessage by viewModel.error

    val itemCounts = remember { mutableStateMapOf<Int, Int>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Меню") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        },
        bottomBar = {
                Button(
                    onClick = {
                        navController.navigate(Screen.Cart.route)
                    },
                    enabled = itemCounts.isNotEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .navigationBarsPadding(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonColor,
                        contentColor = Color.White
                    )
                ) {
                    Text("Перейти к оплате")
                }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(menuItems) { item ->
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(item.imageURL.takeIf { !it.isNullOrBlank() })
                                        .crossfade(true)
                                        .placeholder(R.drawable.coffee)
                                        .error(R.drawable.coffee)
                                        .build(),
                                    contentDescription = item.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp)
                                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                                )

                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 8.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = item.name,
                                        style = MaterialTheme.typography.titleMedium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "${item.price.toInt()} ₽",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = buttonColor,
                                        )
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            IconButton(onClick = {
                                                val current = itemCounts[item.id] ?: 0
                                                if (current > 0) itemCounts[item.id] = current - 1
                                            }) {
                                                Text(
                                                    "-",
                                                    style = MaterialTheme.typography.titleLarge,
                                                    color = buttonColor
                                                )
                                            }

                                            Text(
                                                text = "${itemCounts[item.id] ?: 0}",
                                                style = MaterialTheme.typography.bodyLarge,
                                                color = buttonColor,
                                                modifier = Modifier.padding(horizontal = 4.dp)
                                            )

                                            IconButton(onClick = {
                                                val current = itemCounts[item.id] ?: 0
                                                itemCounts[item.id] = current + 1
                                                CartManager.addItem(item)
                                            }) {
                                                Text("+", style = MaterialTheme.typography.titleLarge, color = buttonColor)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
