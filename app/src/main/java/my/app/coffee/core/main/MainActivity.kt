package my.app.coffee.core.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.yandex.mapkit.MapKitFactory
import my.app.coffee.BuildConfig
import my.app.coffee.ui.navigation.AppNavGraph
import my.app.coffee.ui.theme.CoffeeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.setApiKey(BuildConfig.MAPS_KEY)

        enableEdgeToEdge()
        setContent {
            CoffeeTheme {
                val navController = rememberNavController()
                AppNavGraph(navController)
            }
        }
    }
}