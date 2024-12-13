package com.barengsaya.dentassist.data.api.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("idUser")
	val idUser: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
