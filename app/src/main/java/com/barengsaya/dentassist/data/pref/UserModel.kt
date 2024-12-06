package com.barengsaya.dentassist.data.pref

data class UserModel(
    val email: String,
    val idUser: String,
    val isLogin: Boolean = false
)