package com.example.frame.data.repository

import android.util.Log
import com.example.frame.data.local.TokenStore
import com.example.frame.data.remote.ApiClient
import com.example.frame.data.model.login.LoginRequest
import com.example.frame.data.model.signup.SignupRequest
import com.example.frame.data.model.user.ProfileResponse
import okio.IOException

class AuthRepository {

    suspend fun getProfile(): ProfileResponse {
        val token = TokenStore.accessToken
            ?: throw IllegalArgumentException("Please sign in again")

        val response = try {
            ApiClient.authApi.getProfile(authorization = "Bearer $token")
        } catch (exception: IOException) {
            throw IOException(
                "Cannot reach the server. Check your connection",
                exception
            )
        }

        return when {
            response.isSuccessful -> {
                response.body()
                    ?: throw IOException("Server returned an empty profile")
            }

            response.code() == 401 -> {
                TokenStore.clear()
                throw IllegalArgumentException(
                    "Your session has expired. Please sign in again"
                )
            }

            else -> {
                throw IOException("Could not load profile. Please try again")
            }
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
            response.isSuccessful -> {
                val body = response.body()
                    ?: throw IOException("Server returned an empty login response")
                val token = body.accessToken

                if (token.isBlank()) {
                    throw IOException("Server returned an invalid access token")
                }

                TokenStore.saveToken(token)
                true
            }
            response.code() == 401 ->
                throw IllegalArgumentException("Invalid email or password")
            else ->
                throw IOException("Login failed. Please try again")
        }

    }

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

}
