package com.barengsaya.dentassist.di

import android.content.Context
import com.barengsaya.dentassist.data.Repository
import com.barengsaya.dentassist.data.api.retrofit.ApiConfig
import com.barengsaya.dentassist.data.pref.UserPreference
import com.barengsaya.dentassist.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): Repository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(pref, apiService)
    }
}