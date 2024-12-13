package com.dicoding.myevent.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData


class DashboardViewModel : ViewModel() {
//binding ini digunakan untuk menghubungkan layout dengan fragment
    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}