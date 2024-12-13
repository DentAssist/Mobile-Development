package com.barengsaya.dentassist.view.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barengsaya.dentassist.data.Repository
import com.barengsaya.dentassist.data.api.response.HistoryResponse
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: Repository) : ViewModel() {

    fun getHistory(idUser: String): LiveData<HistoryResponse> {
        val historyData = MutableLiveData<HistoryResponse>()
        viewModelScope.launch {
            val response = repository.getHistory(idUser)
            historyData.postValue(response)

        }
        return historyData
    }
}