package com.barengsaya.dentassist.data.api.request

data class UpdateUserRequest(
    val username: String,
    val email: String,
    val city: String
)
