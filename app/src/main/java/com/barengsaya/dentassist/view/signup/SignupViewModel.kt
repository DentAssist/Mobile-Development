package com.barengsaya.dentassist.view.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barengsaya.dentassist.data.Repository
import com.barengsaya.dentassist.data.api.response.SignupResponse
import kotlinx.coroutines.launch

class SignupViewModel(private val repository: Repository) : ViewModel() {

    private val _signupResult = MutableLiveData<SignupResponse?>()
    val signupResult: LiveData<SignupResponse?> = _signupResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun signup(username: String, email: String, password: String, city: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.signup(username, email, password, city)
                _signupResult.value = response
            } catch (e: retrofit2.HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                if (errorBody != null) {
                    try {
                        val gson = com.google.gson.Gson()
                        val errorResponse = gson.fromJson(errorBody, SignupResponse::class.java)
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

