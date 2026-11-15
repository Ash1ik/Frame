package com.example.frame.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frame.data.local.TokenStore
import com.example.frame.data.model.user.ProfileResponse
import com.example.frame.data.repository.AuthRepository
import kotlinx.coroutines.launch
import okio.IOException


class HomeViewModel : ViewModel() {
    private val repository = AuthRepository()
    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        loadProfile()
    }

    fun loadProfile() {
        if (uiState.isLoading) return

        uiState = uiState.copy(
            isLoading = true,
            errorMessage = null,
            isSessionExpired = false
        )

        viewModelScope.launch {
            try {
                val profile = repository.getProfile()
                uiState = uiState.copy(profile = profile)
            } catch (exception: IOException) {
                uiState = uiState.copy(
                    errorMessage = exception.message ?: "Could not load profile"
                )
            } catch (exception: IllegalArgumentException) {
                uiState = uiState.copy(
                    profile = null,
                    isSessionExpired = true,
                    errorMessage = exception.message
                )
            } finally {
                uiState = uiState.copy(isLoading = false)
            }
        }

    }

    fun logout() {
        TokenStore.clear()
        uiState = uiState.copy(isSessionExpired = true)
    }
}
