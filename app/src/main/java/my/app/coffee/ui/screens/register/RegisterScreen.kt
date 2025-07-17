package my.app.coffee.ui.screens.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import my.app.coffee.core.App
import my.app.coffee.ui.navigation.Screen

@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel = remember {
        val app = context.applicationContext as App
        RegisterViewModel(app.appComponent.authApi())
    }

    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    viewModel.onSuccess = {
        loading = false
        navController.navigate(Screen.Login.route)
    }

    viewModel.onError = {
        loading = false
        error = it
    }

    Column(Modifier.padding(16.dp)) {
        Text("Регистрация", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = login, onValueChange = { login = it }, label = { Text("Логин") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = password, onValueChange = { password = it }, label = { Text("Пароль") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                loading = true
                viewModel.register(login, password)
            },
            enabled = !loading
        ) {
            Text("Зарегистрироваться")
        }
        if (error != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(error!!, color = MaterialTheme.colorScheme.error)
        }
    }
}