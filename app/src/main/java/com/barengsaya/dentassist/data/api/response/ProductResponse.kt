package com.barengsaya.dentassist.data.api.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(

	@field:SerializedName("data")
	val data: List<ProductDataItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class ProductDataItem(

	@field:SerializedName("disease")
	val disease: String? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("dosis")
	val dosis: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("idProduct")
	val idProduct: String? = null,

	@field:SerializedName("ket")
	val ket: String? = null,

	@field:SerializedName("link_photo")
	val linkPhoto: String? = null
)
