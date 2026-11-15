package com.example.frame.data.remote

import com.example.frame.data.model.login.LoginRequest
import com.example.frame.data.model.login.LoginResponse
import com.example.frame.data.model.signup.SignupRequest
import com.example.frame.data.model.signup.SignupResponse
import com.example.frame.data.model.user.ProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @GET("users/me")
    suspend fun getProfile(
        @Header("Authorization") authorization: String
    ): Response<ProfileResponse>

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("auth/signup")
    suspend fun signup(
        @Body request: SignupRequest
    ): Response<SignupResponse>

}
