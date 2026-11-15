package com.example.frame

import com.example.frame.data.remote.ApiClient
import com.example.frame.data.login.LoginRequest
import com.example.frame.data.signup.SignupRequest
import okio.IOException

class AuthRepository {

    suspend fun signup(
        name: String,
        email: String,
        password: String
    ): Boolean {
        val request = SignupRequest(
            name = name,
            email = email,
            password = password
        )

        val response = try {
            ApiClient.authApi.signup(request)
        } catch (exception: IOException) {
            throw IOException(
                "Cannot reach the server. Check your connection",
                exception
            )
        }

        return when {
            response.code() == 201 -> true

            response.code() == 409 ->
                throw IllegalArgumentException("This email is already registered")

            response.code() == 400 ->
                throw IllegalArgumentException("Please check your signup details")

            else ->
                throw IOException("Signup failed. Please try again")
        }
    }

    suspend fun login(
        email: String,
        password: String
    ): Boolean {
        val request = LoginRequest(
            email = email,
            password = password
        )

        val response = try {
            ApiClient.authApi.login(request)
        } catch (exception: IOException) {
            throw IOException(
                "Cannot reach the server. Check your connection",
                exception
            )
        }

        return when {
            response.isSuccessful -> true
            response.code() == 401 ->
                throw IllegalArgumentException("Invalid email or password")
            else ->
                throw IOException("Login failed. Please try again")
        }

    }

}