package com.barengsaya.dentassist.data

import com.barengsaya.dentassist.data.api.request.SignupRequest
import com.barengsaya.dentassist.data.api.response.SignupResponse
import com.barengsaya.dentassist.data.api.retrofit.ApiService
import com.barengsaya.dentassist.data.pref.UserModel
import com.barengsaya.dentassist.data.pref.UserPreference
import kotlinx.coroutines.flow.Flow

class Repository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun signup(username: String, email: String, password: String): SignupResponse {
        val request = SignupRequest(username, email, password)
        return apiService.signup(request)
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(userPreference, apiService)
            }.also { instance = it }
    }
}