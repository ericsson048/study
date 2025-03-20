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
    val repository: Repository
) :ViewModel() {
    private var _state = MutableStateFlow<AppState>(AppState())
    val allUsers = repository.getAllUsers().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        emptyList()
    )

    var state = combine(_state, allUsers) { _state, users ->
        _state.copy(allUsers = users)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        AppState()
    )

    fun insertUser() {
        val User = Users(
            first_name = state.value.first_name.value,
            last_nane = state.value.last_name.value,
            email = state.value.email.value,
            password = state.value.password.value
        )

        viewModelScope.launch { repository.insertUser(User) }


    }
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
