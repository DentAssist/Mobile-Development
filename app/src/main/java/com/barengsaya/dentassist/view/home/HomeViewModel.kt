package com.barengsaya.dentassist.view.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.barengsaya.dentassist.data.Repository
import com.barengsaya.dentassist.data.api.response.UserResponse
import com.barengsaya.dentassist.data.pref.UserModel
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {
    private val _userProfile = MutableLiveData<UserResponse>()
    val userProfile: LiveData<UserResponse> get() = _userProfile

    fun fetchUserProfile(idUser: String) {
        viewModelScope.launch {
            try {
                val response = repository.getUserProfile(idUser)
                _userProfile.value = response
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error loading profile: ${e.message}", e)
            }
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}
