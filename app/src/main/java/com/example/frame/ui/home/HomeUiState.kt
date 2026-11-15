package com.example.frame.ui.home

import com.example.frame.data.model.user.ProfileResponse

data class HomeUiState(
    val isLoading: Boolean = false,
    val profile: ProfileResponse? = null,
    val errorMessage: String? = null,
    val isSessionExpired: Boolean = false
)
