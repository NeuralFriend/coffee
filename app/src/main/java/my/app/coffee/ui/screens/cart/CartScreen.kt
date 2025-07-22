package my.app.coffee.ui.screens.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import my.app.coffee.ui.theme.buttonColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController) {
    val viewModel: CartViewModel = viewModel()
    val cartItems = viewModel.cartItems
    val totalPrice = viewModel.totalPrice()
    val ordered = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ваш заказ") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Итого: ${totalPrice.toInt()} ₽",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Button(
                    onClick = {
                        ordered.value = true
                        viewModel.clear()
                    },
                    enabled = totalPrice > 0,
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonColor,
                        contentColor = Color.White
                    )
                ) {
                    Text("Оплатить")
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(cartItems, key = { it.item.id }) { cartItem ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(cartItem.item.name, style = MaterialTheme.typography.titleMedium)
                        Text(
                            "${cartItem.item.price.toInt()} ₽",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {
                            viewModel.decrease(cartItem.item.id)
                        }) {
                            Text("-", style = MaterialTheme.typography.titleLarge)
                        }

                        Text("${cartItem.count.value}", modifier = Modifier.padding(horizontal = 8.dp))

                        IconButton(onClick = {
                            viewModel.increase(cartItem.item.id)
                        }) {
                            Text("+", style = MaterialTheme.typography.titleLarge)
                        }
                    }
                }
            }

            if (ordered.value) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Ваш заказ будет готов через 15 минут.\nСпасибо, что выбрали нас!",
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                            color = buttonColor
                        )
                    }
                }
            }
        }
    }
}
