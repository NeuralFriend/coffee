package my.app.coffee.ui.screens.register

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import my.app.coffee.data.AuthApi
import my.app.coffee.model.AuthRequest

class RegisterViewModel(
    private val authApi: AuthApi,
    private val prefs: SharedPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun register(login: String, password: String, confirmPassword: String) {
        if (password != confirmPassword) {
            _uiState.value = RegisterUiState.ValidationError("Пароли не совпадают")
            return
        }

        viewModelScope.launch {
            _uiState.value = RegisterUiState.Loading
            try {
                val response = authApi.register(AuthRequest(login, password))
                prefs.edit()
                    .putString("access_token", response.token)
                    .apply()

                _uiState.value = RegisterUiState.Success
            } catch (e: Exception) {
                _uiState.value = RegisterUiState.Error(e.localizedMessage ?: "Неизвестная ошибка")
            }
        }
    }

    fun resetState() {
        _uiState.value = RegisterUiState.Idle
    }
}

sealed class RegisterUiState {
    object Idle : RegisterUiState()
    object Loading : RegisterUiState()
    object Success : RegisterUiState()
    data class Error(val message: String) : RegisterUiState()
    data class ValidationError(val message: String) : RegisterUiState()
}