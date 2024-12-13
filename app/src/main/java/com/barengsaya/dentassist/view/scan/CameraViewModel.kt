package com.barengsaya.dentassist.view.scan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barengsaya.dentassist.data.Repository
import com.barengsaya.dentassist.data.api.response.PredictResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CameraViewModel(private val repository: Repository) : ViewModel() {

    val predictResult = MutableLiveData<Result<PredictResponse>>()

    fun predict(imageFile: MultipartBody.Part, idUser: RequestBody) {
        viewModelScope.launch {
            try {
                val response = repository.predict(imageFile, idUser)
                predictResult.postValue(Result.success(response))
            } catch (e: Exception) {
                predictResult.postValue(Result.failure(e))
            }
        }
    }
}
