package com.barengsaya.dentassist.data.api.retrofit

import com.barengsaya.dentassist.data.api.request.LoginRequest
import com.barengsaya.dentassist.data.api.request.SignupRequest
import com.barengsaya.dentassist.data.api.request.UpdateUserRequest
import com.barengsaya.dentassist.data.api.response.LoginResponse
import com.barengsaya.dentassist.data.api.response.SignupResponse
import com.barengsaya.dentassist.data.api.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("auth/signup")
    suspend fun signup(
        @Body request: SignupRequest
    ): SignupResponse

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("users/{idUser}")
    suspend fun user(
        @Path("idUser") idUser: String): UserResponse

    @PUT("users/{idUser}")
    suspend fun updateUserProfile(
        @Path("idUser") idUser: String,
        @Body request: UpdateUserRequest
    ): UserResponse



    @POST("logout")
    suspend fun logout(): SignupResponse
}

