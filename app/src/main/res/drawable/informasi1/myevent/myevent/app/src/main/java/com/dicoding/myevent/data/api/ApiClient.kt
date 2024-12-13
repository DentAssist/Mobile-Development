package com.dicoding.myevent.data.api

import android.content.Context
import com.dicoding.myevent.data.model.EventListResponse
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient {

    companion object{
        fun getApiService(context: Context): ApiService {
            val loggingInterceptor = ChuckerInterceptor(context)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("coba-api.dicoding.dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }

}
interface ApiService {

    @GET("events")
    fun getEvents(
        @Query("active") active: Int = 0,
        @Query("q") query: String,
        @Query("limit") limit: Int = 10,
    ): Call<EventListResponse>



}