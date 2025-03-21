package iut.montpellier.study.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import iut.montpellier.study.data.entity.Users
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private var _state = MutableStateFlow(AppState())
    val allUsers = repository.getAllUsers().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    var state = combine(_state, allUsers) { state, users ->
        state.copy(allUsers = users)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = AppState()
    )

    // New State for Login
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState = _loginState.asStateFlow() // Expose as read-only StateFlow

    fun insertUser() {
        val User = Users(
            first_name = state.value.first_name.value,
            last_nane = state.value.last_name.value,
            email = state.value.email.value,
            password = state.value.password.value
        )

        viewModelScope.launch { repository.insertUser(User) }
    }

    fun loginUser(email: String, password: String) {
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            try {
                val isValid = repository.isUserValid(email, password)
                if (isValid) {
                    _loginState.value = LoginState.Success
                } else {
                    _loginState.value = LoginState.Error("Invalid credentials")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("An error occurred: ${e.message}")
            }
        }
    }

    fun updateFirstName(newValue:String) {
        _state.value = _state.value.copy(first_name = mutableStateOf(newValue))
    }

    fun updateLastName(newValue:String) {
        _state.value = _state.value.copy(last_name = mutableStateOf(newValue))
    }
    fun updateEmail(newValue:String) {
        _state.value = _state.value.copy(email = mutableStateOf(newValue))
    }

    fun updatePassWord(newValue:String) {
        _state.value = _state.value.copy(password = mutableStateOf(newValue))
    }

}

// New LoginState sealed class
sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}

data class AppState(
    var loading: Boolean = false,
    var allUsers : List<Users> = emptyList<Users>(),
    var error: String= "",
    var first_name: MutableState<String> = mutableStateOf(""),
    var last_name:MutableState<String> = mutableStateOf(""),
    var email:MutableState<String> = mutableStateOf(""),
    var password:MutableState<String> = mutableStateOf("")
)
