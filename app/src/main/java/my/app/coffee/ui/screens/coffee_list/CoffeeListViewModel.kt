package my.app.coffee.ui.screens.coffee_list

import android.location.Location
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.launch
import my.app.coffee.data.ApiService
import my.app.coffee.model.CoffeeLocation

class CoffeeListViewModel(
    private val api: ApiService,
    private val locationClient: FusedLocationProviderClient
) : ViewModel() {

    private val _locations = mutableStateOf<List<CoffeeLocation>>(emptyList())
    val locations: State<List<CoffeeLocation>> = _locations

    private val _userLocation = mutableStateOf<Location?>(null)
    val userLocation: State<Location?> = _userLocation

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    fun loadLocations() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _locations.value = api.getLocations()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadUserLocation() {
        locationClient.lastLocation
            .addOnSuccessListener { location ->
                _userLocation.value = location
            }
            .addOnFailureListener { exception ->
                _error.value = exception.message
            }
    }

    fun getDistanceTo(location: CoffeeLocation): String {
        val userLoc = _userLocation.value ?: return "—"
        val placeLoc = Location("").apply {
            latitude = location.point.latitude
            longitude = location.point.longitude
        }

        val meters = userLoc.distanceTo(placeLoc)
        return if (meters >= 1000) {
            String.format("%.1f км", meters / 1000)
        } else {
            "${meters.toInt()} м"
        }
    }
}