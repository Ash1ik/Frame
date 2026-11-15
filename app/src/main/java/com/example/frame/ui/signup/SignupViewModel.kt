package com.example.frame.ui.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frame.data.repository.AuthRepository
import kotlinx.coroutines.launch
import okio.IOException

class SignupViewModel: ViewModel() {

    private val repository = AuthRepository()

    var uiState by mutableStateOf(SignupUiState())
        private set

    fun signup(name: String, email: String, password: String) {
        if (uiState.isLoading) return

        uiState = uiState.copy(
            isLoading = true,
            isSignupSuccessful = false,
            errorMessage = null
        )

        viewModelScope.launch {
            try {
                val success = repository.signup(name, email, password)

                uiState = uiState.copy(isSignupSuccessful = success)
            } catch (e: IOException) {
                uiState = uiState.copy(
                    errorMessage = e.message ?: "Cannot connect to the server"
                )
            } catch (e: IllegalArgumentException) {
                uiState = uiState.copy(
                    errorMessage = e.message ?: "Please check your signup details"
                )
            } finally {
                uiState = uiState.copy(
                    isLoading = false
                )
            }
        }

    }

}
