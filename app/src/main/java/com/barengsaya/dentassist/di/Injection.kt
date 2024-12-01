package com.barengsaya.dentassist.di

import android.content.Context
import com.barengsaya.dentassist.data.UserRepository
import com.barengsaya.dentassist.data.pref.UserPreference
import com.barengsaya.dentassist.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}