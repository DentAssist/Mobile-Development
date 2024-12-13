package com.barengsaya.dentassist.data.api.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class PredictResponse(

	@field:SerializedName("data")
	val data: PredictionData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class ProductsItem(

	@field:SerializedName("disease")
	val disease: String? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("dosis")
	val dosis: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("ket")
	val ket: String? = null,

	@field:SerializedName("link_photo")
	val linkPhoto: String? = null
) : Parcelable

@Parcelize
data class ArticlesItem(

	@field:SerializedName("disease")
	val disease: String? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("name")
	val name: String? = null
) : Parcelable

@Parcelize
data class PredictionData(

	@field:SerializedName("idUser")
	val idUser: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("confidenceScore")
	val confidenceScore: Double? = null,

	@field:SerializedName("signedUrl")
	val signedUrl: String? = null,

	@field:SerializedName("suggestion")
	val suggestion: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("label")
	val label: String? = null,

	@field:SerializedName("clinic")
	val clinic: Clinic? = null,

	@field:SerializedName("explanation")
	val explanation: String? = null,

	@field:SerializedName("articles")
	val articles: List<ArticlesItem?>? = null,

	@field:SerializedName("products")
	val products: List<ProductsItem?>? = null
) : Parcelable

@Parcelize
data class Clinic(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("link_maps")
	val linkMaps: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("no_telp")
	val noTelp: String? = null
) : Parcelable
