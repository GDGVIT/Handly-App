package com.dscvit.handly.network

import com.dscvit.handly.model.auth.AuthResponse
import com.dscvit.handly.model.auth.SigninRequest
import com.dscvit.handly.model.auth.SignupRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("user/create/")
    suspend fun signupUser(@Body signupRequest: SignupRequest): Response<AuthResponse>

    @POST("user/login")
    suspend fun signinUser(@Body signinRequest: SigninRequest): Response<AuthResponse>

}