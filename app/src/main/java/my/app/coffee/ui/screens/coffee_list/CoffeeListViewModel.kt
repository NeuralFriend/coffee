package my.app.coffee.ui.screens.coffee_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import my.app.coffee.data.ApiService
import my.app.coffee.model.CoffeeLocation

class CoffeeListViewModel(private val api: ApiService) : ViewModel() {

    var locations by mutableStateOf<List<CoffeeLocation>>(emptyList())
        private set

    init {
        loadLocations()
    }

    private fun loadLocations() {
        viewModelScope.launch {
            try {
                locations = api.getLocations()
            } catch (e: Exception) {
            }
        }
    }
}