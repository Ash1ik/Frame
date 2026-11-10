package com.example.frame

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    private val repository = AuthRepository()
    var uiState by mutableStateOf(LoginUiState())
        private set

    fun login(email: String, password: String) {

        if (uiState.isLoading) return

        uiState = uiState.copy(
            isLoading = true,
            isLoggedIn = false,
            errorMessage = null
        )

        viewModelScope.launch {
            val success = repository.login(email,password)

            uiState = uiState.copy(
                isLoading = false,
                isLoggedIn = success,
                errorMessage = if (success) {
                    null
                } else {
                    "Email or password is incorrect"
                }
            )
        }

    }

}