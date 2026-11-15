package com.example.frame.ui.signup

data class SignupUiState (
    val isLoading: Boolean = false,
    val isSignupSuccessful: Boolean = false,
    val errorMessage: String? = null
)
