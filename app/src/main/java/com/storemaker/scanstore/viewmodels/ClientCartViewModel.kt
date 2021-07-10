package com.storemaker.scanstore.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.storemaker.scanstore.network.models.Cart
import com.storemaker.scanstore.repositories.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClientCartViewModel: ViewModel() {
    private val cartRepo = CartRepository()
    val cartsList: MutableLiveData<List<Cart>> = MutableLiveData(emptyList())

    fun getCartByUser(
        userId: String,
    ) =
        viewModelScope.launch(Dispatchers.IO) {
            val resp = cartRepo.getCartsByUserAsync(
                userId,
            ).await()
            if (resp.isSuccessful) {
                val body = resp.body()
                cartsList.postValue(body!!.carts)
            } else {
                Log.d("CUSTOM", resp.message())
            }

        }
}