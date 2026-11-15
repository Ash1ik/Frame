package com.example.frame.signup.viewmodel

data class SignupUiState (
    val isLoading: Boolean = false,
    val isSignupSuccessful: Boolean = false,
    val errorMessage: String? = null
)