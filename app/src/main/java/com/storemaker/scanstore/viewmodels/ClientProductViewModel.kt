package com.storemaker.scanstore.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.storemaker.scanstore.network.models.Product
import com.storemaker.scanstore.repositories.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClientProductViewModel: ViewModel() {
    private val productRepo = ProductRepository()

    val productList: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    fun retrieveProducts() = viewModelScope.launch(Dispatchers.IO) {
        val resp = productRepo.getProductsAsync().await()
        if (resp.isSuccessful) {
            val body = resp.body()
            productList.postValue(body!!.products)
        }
    }
}