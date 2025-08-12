package androidlead.weatherappui.ui.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidlead.weatherappui.ui.auth.AuthRepository
import androidlead.weatherappui.ui.auth.SessionDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val loading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository,
    private val session: SessionDataStore
) : ViewModel() {

    private val _ui = MutableStateFlow(AuthUiState())
    val ui = _ui.asStateFlow()

    fun login(username: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _ui.value = AuthUiState(loading = true)
            val res = repo.login(username, password)
            if (res.isSuccess) {
                session.setLoggedIn(username)
                _ui.value = AuthUiState()
                onSuccess()
            } else {
                _ui.value = AuthUiState(error = res.exceptionOrNull()?.message ?: "Login failed")
            }
        }
    }

    fun signUp(username: String, email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _ui.value = AuthUiState(loading = true)
            val res = repo.signUp(username, email, password)
            if (res.isSuccess) {
                session.setLoggedIn(username)
                _ui.value = AuthUiState()
                onSuccess()
            } else {
                _ui.value = AuthUiState(error = res.exceptionOrNull()?.message ?: "Sign up failed")
            }
        }
    }
}
