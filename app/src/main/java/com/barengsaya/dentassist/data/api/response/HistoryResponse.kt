package com.barengsaya.dentassist.data.api.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class HistoryResponse(

	@field:SerializedName("data")
	val data: List<DataItemHistory>,

	@field:SerializedName("status")
	val status: String
) : Parcelable

@Parcelize
data class DataItemHistory(

	@field:SerializedName("idUser")
	val idUser: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("confidenceScore")
	val confidenceScore: Double? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String,

	@field:SerializedName("suggestion")
	val suggestion: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("label")
	val label: String,

	@field:SerializedName("clinic")
	val clinic: Clinic,

	@field:SerializedName("explanation")
	val explanation: String,

	@field:SerializedName("articles")
	val articles: List<ArticlesItem>,

	@field:SerializedName("products")
	val products: List<ProductsItem>
) : Parcelable

@Parcelize
data class ArticlesItemHistory(

	@field:SerializedName("disease")
	val disease: String,

	@field:SerializedName("link")
	val link: String,

	@field:SerializedName("name")
	val name: String
) : Parcelable

@Parcelize
data class ProductsItemHistory(

	@field:SerializedName("disease")
	val disease: String,

	@field:SerializedName("price")
	val price: String,

	@field:SerializedName("dosis")
	val dosis: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("ket")
	val ket: String,

	@field:SerializedName("link_photo")
	val linkPhoto: String
) : Parcelable

@Parcelize
data class ClinicHistory(

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("link_maps")
	val linkMaps: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("photo")
	val photo: String,

	@field:SerializedName("no_telp")
	val noTelp: String
) : Parcelable
