package com.barengsaya.dentassist.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barengsaya.dentassist.data.UserRepository
import com.barengsaya.dentassist.data.pref.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}