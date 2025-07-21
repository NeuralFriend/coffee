package my.app.coffee.ui.screens.coffee_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import my.app.coffee.data.ApiService
import my.app.coffee.model.CoffeeLocation

class CoffeeListViewModel(private val api: ApiService) : ViewModel() {

    private val _locations = mutableStateOf<List<CoffeeLocation>>(emptyList())
    val locations: State<List<CoffeeLocation>> = _locations

    init {
        loadLocations()
    }

    fun loadLocations() {
        viewModelScope.launch {
            try {
                val response = api.getLocations()
                Log.d("LOCATIONS", "Загружено: ${response.size}")
                _locations.value = response
            } catch (e: Exception) {
                Log.e("LOCATIONS", "Ошибка: ${e.message}")
            }
        }
    }
}
