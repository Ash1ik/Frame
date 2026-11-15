package com.example.frame.data.model.login

data class LoginResponse(
    val id: Long,
    val name: String,
    val email: String,
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Long
)
