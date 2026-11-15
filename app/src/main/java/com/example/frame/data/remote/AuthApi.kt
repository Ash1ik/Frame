package com.example.frame.data.remote

import com.example.frame.data.login.LoginRequest
import com.example.frame.data.login.LoginResponse
import com.example.frame.data.signup.SignupRequest
import com.example.frame.data.signup.SignupResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("auth/signup")
    suspend fun signup(
        @Body request: SignupRequest
    ): Response<SignupResponse>

}