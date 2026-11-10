package com.example.frame

import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class AuthRepository {

    suspend fun login(
        email: String,
        password: String
    ): Boolean {
        delay(2000.milliseconds)

        return email == "ashik@frame.com" && password == "123456"
    }

}