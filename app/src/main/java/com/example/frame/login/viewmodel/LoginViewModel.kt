package com.example.frame.login.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frame.AuthRepository
import kotlinx.coroutines.launch
import okio.IOException

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
            try {
                val success = repository.login(email,password)

                uiState = uiState.copy(
                    isLoggedIn = success
                )
            } catch (exception: IOException) {
                uiState = uiState.copy(
                    errorMessage = exception.message ?: "Cannot connect to the server"
                )
            } catch (exception: IllegalArgumentException) {
                uiState = uiState.copy(
                    errorMessage = exception.message
                        ?: "Invalid email or password"
                )
            } finally {
                uiState = uiState.copy(
                    isLoading = false
                )
            }

        }

    }

}