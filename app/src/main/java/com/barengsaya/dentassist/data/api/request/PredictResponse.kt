package com.barengsaya.dentassist.data.api.request

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.RawValue

@Parcelize
data class PredictResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class Data(

	@field:SerializedName("idClinic")
	val idClinic: String? = null,

	@field:SerializedName("product")
	val product: String? = null,

	@field:SerializedName("idArticle")
	val idArticle: String? = null,

	@field:SerializedName("suggestion")
	val suggestion: String? = null,

	@field:SerializedName("label")
	val label: String? = null,

	@field:SerializedName("explanation")
	val explanation: String? = null,

	@field:SerializedName("article")
	val article: String? = null,

	@field:SerializedName("idUser")
	val idUser: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("confidenceScore")
	val confidenceScore: @RawValue Any? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("idProduct")
	val idProduct: String? = null,

	@field:SerializedName("id")
	val id: String? = null
) : Parcelable
