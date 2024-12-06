package com.barengsaya.dentassist.data.api.retrofit

import com.barengsaya.dentassist.data.api.request.SignupRequest
import com.barengsaya.dentassist.data.api.response.SignupResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("signup")
    suspend fun signup(
        @Body request: SignupRequest
    ): SignupResponse
}

