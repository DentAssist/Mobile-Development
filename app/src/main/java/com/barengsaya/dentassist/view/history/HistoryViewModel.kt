package com.barengsaya.dentassist.view.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barengsaya.dentassist.data.Repository
import com.barengsaya.dentassist.data.api.response.HistoryResponse
import kotlinx.coroutines.launch
import java.lang.Exception

class HistoryViewModel(private val repository: Repository) : ViewModel() {

    fun getHistory(idUser: String): LiveData<HistoryResponse> {
        val historyData = MutableLiveData<HistoryResponse>()
        viewModelScope.launch {
            try {
                val response = repository.getHistory(idUser)
                historyData.postValue(response)
            } catch (e: Exception) {
                // Tangani kesalahan jika perlu
            }
        }
        return historyData
    }
}