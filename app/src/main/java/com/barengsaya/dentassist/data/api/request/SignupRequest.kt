package com.barengsaya.dentassist.data.api.request

data class SignupRequest(
    val username: String,
    val email: String,
    val password: String,
    val city: String
)
