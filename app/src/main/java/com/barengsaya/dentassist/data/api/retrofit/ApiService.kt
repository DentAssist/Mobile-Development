package com.barengsaya.dentassist.data.api.retrofit

import com.barengsaya.dentassist.data.api.request.LoginRequest
import com.barengsaya.dentassist.data.api.request.SignupRequest
import com.barengsaya.dentassist.data.api.response.LoginResponse
import com.barengsaya.dentassist.data.api.response.SignupResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/signup")
    suspend fun signup(
        @Body request: SignupRequest
    ): SignupResponse

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("logout")
    suspend fun logout(): SignupResponse
}

