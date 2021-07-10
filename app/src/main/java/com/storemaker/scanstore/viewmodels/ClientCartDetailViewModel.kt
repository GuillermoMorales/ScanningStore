package com.storemaker.scanstore.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.storemaker.scanstore.network.models.Cart
import com.storemaker.scanstore.repositories.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClientCartDetailViewModel:ViewModel() {
    private val cartRepo = CartRepository()
    val cartsList: MutableLiveData<List<Cart>> = MutableLiveData(emptyList())
    val cartFound: MutableLiveData<Cart?> = MutableLiveData(null)

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
    fun getCartById(
        cartId: String,
    ) =
        viewModelScope.launch(Dispatchers.IO) {
            val resp = cartRepo.getCartAsync(
                cartId,
            ).await()
            if (resp.isSuccessful) {
                val body = resp.body()
                cartFound.postValue(body!!.cart)
            } else {
                Log.d("CUSTOM", resp.message())
            }

        }
}