package my.app.coffee.core.navigation

sealed class Screen(val route: String) {
    object Start : Screen("start")
    object Register : Screen("register")
    object Login : Screen("login")
    object CoffeeList : Screen("coffee_list")
    object Map : Screen("map")
    object Menu : Screen("menu/{id}") {
        fun createRoute(id: Int) = "menu/$id"
    }
    object Cart : Screen("cart")
}