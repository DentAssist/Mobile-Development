package com.barengsaya.dentassist.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.barengsaya.dentassist.data.UserRepository
import com.barengsaya.dentassist.data.pref.UserModel
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

}