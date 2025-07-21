package my.app.coffee.ui.screens.map

import MapsView
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.scale
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.runtime.image.ImageProvider
import my.app.coffee.R
import my.app.coffee.core.App
import my.app.coffee.core.di.CoffeeListViewModelFactory
import my.app.coffee.ui.screens.coffee_list.CoffeeListViewModel

@Composable
fun MapScreen(navController: NavController) {
    val context = LocalContext.current
    val app = context.applicationContext as App
    val factory = CoffeeListViewModelFactory(app.appComponent.apiService())
    val viewModel: CoffeeListViewModel = viewModel(factory = factory)
    val locations by viewModel.locations

    LifeScreen(
        onStart = {
            MapKitFactory.initialize(context)
            MapKitFactory.getInstance().onStart()
        },
        onStop = {
            MapKitFactory.getInstance().onStop()
        },
    )

    if (locations.isNotEmpty()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                MapsView(ctx).apply {
                    val originalBitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.coffee)
                    val smallBitmap = originalBitmap.scale(64, 64)
                    val imageProvider = ImageProvider.fromBitmap(smallBitmap)
                    val pinsCollection = mapWindow.map.mapObjects.addCollection()

                    for (place in locations) {
                        val point = Point(place.point.latitude, place.point.longitude)
                        val placemark = pinsCollection.addPlacemark().apply {
                            geometry = point
                            setIcon(imageProvider)
                        }
                        placemark.addTapListener { _, _ ->
                            Toast.makeText(
                                ctx,
                                "${place.name}\nШирота: ${point.latitude}, Долгота: ${point.longitude}",
                                Toast.LENGTH_SHORT
                            ).show()
                            true
                        }
                    }

                    val firstPoint = Point(locations.first().point.latitude, locations.first().point.longitude)
                    mapWindow.map.move(CameraPosition(firstPoint, 14.5f, 0f, 0f))
                }
            }
        )
    }
}