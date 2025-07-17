package my.app.coffee.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import my.app.coffee.data.AuthApi
import my.app.coffee.model.AuthRequest
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val authApi: AuthApi
) : ViewModel() {

    var onSuccess: (() -> Unit)? = null
    var onError: ((String) -> Unit)? = null

    fun register(login: String, password: String) {
        viewModelScope.launch {
            try {
                val response = authApi.register(AuthRequest(login, password))
                // Можно сохранить токен или перейти дальше
                onSuccess?.invoke()
            } catch (e: Exception) {
                onError?.invoke(e.message ?: "Ошибка")
            }
        }
    }
}