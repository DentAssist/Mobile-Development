package com.barengsaya.denasist.di

import android.content.Context
import com.barengsaya.denasist.data.UserRepository
import com.barengsaya.denasist.data.pref.UserPreference
import com.barengsaya.denasist.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}