package my.app.coffee.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import my.app.coffee.ui.screens.start.StartScreen
import my.app.coffee.ui.screens.cart.CartScreen
import my.app.coffee.ui.screens.coffee_list.CoffeeListScreen
import my.app.coffee.ui.screens.login.LoginScreen
import my.app.coffee.ui.screens.map.MapScreen
import my.app.coffee.ui.screens.menu.MenuScreen
import my.app.coffee.ui.screens.register.RegisterScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Start.route) {

        composable(Screen.Start.route) {
            StartScreen(navController)
        }

        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }

        composable(Screen.Login.route) {
            LoginScreen(navController)
        }

        composable(Screen.CoffeeList.route) {
            CoffeeListScreen(navController)
        }

        composable(Screen.Map.route) {
            MapScreen(navController)
        }

        composable("menu/{locationId}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("locationId")?.toIntOrNull()
            if (id != null) {
                MenuScreen(navController, id)
            }
        }

        composable(Screen.Cart.route) {
            CartScreen(navController)
        }
    }
}