package com.ggperroni.challengemobiledev.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ggperroni.challengemobiledev.data.login.LoginRepository
import com.ggperroni.challengemobiledev.domain.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _state = MutableStateFlow<LoginState<String>>(LoginState.Idle)
    val state = _state.asStateFlow()

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    fun onUsernameChange(newUsername: String) {
        _username.value = newUsername
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun login() {
        viewModelScope.launch {
            _state.value = LoginState.Loading
            _state.value = loginRepository.login(username.value, password.value)
        }
    }

    fun resetState() {
        _state.value = LoginState.Idle
    }
}
