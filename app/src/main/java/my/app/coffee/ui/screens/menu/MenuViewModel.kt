package my.app.coffee.ui.screens.menu

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import my.app.coffee.data.ApiService
import my.app.coffee.model.MenuItem

class MenuViewModel(
    private val apiService: ApiService,
    private val locationId: Int
) : ViewModel() {

    private val _menu = mutableStateOf<List<MenuItem>>(emptyList())
    val menu: State<List<MenuItem>> = _menu

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    init {
        loadMenu()
    }

    private fun loadMenu() {
        viewModelScope.launch {
            try {
                _menu.value = apiService.getMenu(locationId)
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Ошибка загрузки меню"
            }
        }
    }
}