package com.barengsaya.dentassist.view.clinik

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barengsaya.dentassist.data.Repository
import com.barengsaya.dentassist.data.api.response.ClinikResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClinikViewModel(private val repository: Repository) : ViewModel() {

    private val _clinics = MutableStateFlow<ClinikResponse?>(null)
    val clinics: StateFlow<ClinikResponse?> = _clinics

    fun fetchClinics() {
        viewModelScope.launch {
            try {
                val response = repository.getClinics()
                _clinics.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
