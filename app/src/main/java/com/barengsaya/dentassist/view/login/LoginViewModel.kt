package com.barengsaya.dentassist.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barengsaya.dentassist.data.Repository
import com.barengsaya.dentassist.data.pref.UserModel
import kotlinx.coroutines.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.barengsaya.dentassist.data.api.response.LoginResponse


class LoginViewModel(private val repository: Repository) : ViewModel() {
    private val _loginResult = MutableLiveData<LoginResponse?>()
    val loginResult: LiveData<LoginResponse?> = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.login(email, password)
                repository.saveSession(
                    UserModel(
                        email = email,
                        idUser = response.idUser ?: "",
                        isLogin = true
                    )
                )
                _loginResult.value = response
            }catch (e: retrofit2.HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                if (errorBody != null) {
                    try {
                        val gson = com.google.gson.Gson()
                        val errorResponse = gson.fromJson(errorBody, LoginResponse::class.java)
                        _errorMessage.value = errorResponse.message ?: "Kesalahan tidak diketahui"
                    } catch (ex: Exception) {
                        _errorMessage.value = "Kesalahan tidak diketahui"
                    }
                } else {
                    _errorMessage.value = "Kesalahan tidak diketahui"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Terjadi kesalahan"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
