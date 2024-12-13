package com.barengsaya.dentassist.view.obat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barengsaya.dentassist.data.Repository
import com.barengsaya.dentassist.data.api.response.ProductDataItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: Repository) : ViewModel() {

    private val _products = MutableStateFlow<List<ProductDataItem>?>(null)
    val products: StateFlow<List<ProductDataItem>?> get() = _products

    fun fetchProducts() {
        viewModelScope.launch {
            try {
                val response = repository.getProducts()
                _products.value = response.data?.filterNotNull()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
