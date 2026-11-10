package com.example.frame

data class LoginUiState (
    val isLoading: Boolean = false,
    val isLoggedIn : Boolean = false,
    val errorMessage: String? = null
)