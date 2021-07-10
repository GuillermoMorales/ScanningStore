package com.storemaker.scanstore.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.storemaker.scanstore.network.models.Cart
import com.storemaker.scanstore.network.models.Product
import com.storemaker.scanstore.network.models.Users
import com.storemaker.scanstore.repositories.CartRepository
import com.storemaker.scanstore.repositories.ProductRepository
import com.storemaker.scanstore.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClientProductInfoViewModel : ViewModel() {

    private val productRepo = ProductRepository()
    private val cartRepo = CartRepository()
    private val userRepo= UserRepository()

    val productList: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val cartsList: MutableLiveData<List<Cart>> = MutableLiveData(emptyList())

    val cartFound: MutableLiveData<Cart?> = MutableLiveData(null)
    val productFound: MutableLiveData<Product?> = MutableLiveData(null)
    val cartInsertedId: MutableLiveData<String> = MutableLiveData("")
    val cartDeletedId: MutableLiveData<String> = MutableLiveData("")
    val userFromSingleQuery:MutableLiveData<Users?> = MutableLiveData(null)


    fun retrieveCart(id: String) = viewModelScope.launch(Dispatchers.IO) {
        val resp = cartRepo.getCartAsync(id).await()
        if (resp.isSuccessful) {
            val body = resp.body()
            cartFound.postValue(body!!.cart)
        }
    }

    fun retrieveProducts() = viewModelScope.launch(Dispatchers.IO) {
        val resp = productRepo.getProductsAsync().await()
        if (resp.isSuccessful) {
            val body = resp.body()
            productList.postValue(body!!.products)
        }
    }

    fun retrieveProduct(id: String) = viewModelScope.launch(Dispatchers.IO) {
        val resp = productRepo.getProductAsync(id).await()
        if (resp.isSuccessful) {
            val body = resp.body()
            productFound.postValue(body!!.product)
        }
    }

    fun saveCart(
        userId: String,
        productId: String,
        quantity: Int,

        ) =
        viewModelScope.launch(Dispatchers.IO) {
            val resp = cartRepo.saveCartAsync(
                userId,
                productId,
                quantity
            ).await()
            if (resp.isSuccessful) {
                val body = resp.body()
                cartInsertedId.postValue(body!!.cart.id)
            } else {
                Log.d("CUSTOM", resp.message())
            }

        }

    fun deleteCart(
        userId: String,
    ) =
        viewModelScope.launch(Dispatchers.IO) {
            val resp = cartRepo.deleteCartAsync(
                userId,
            ).await()
            if (resp.isSuccessful) {
                val body = resp.body()
                cartDeletedId.postValue(body!!.cart.id)
            } else {
                Log.d("CUSTOM", resp.message())
            }

        }
    fun retrieveUser(id:String,name:String) = viewModelScope.launch (Dispatchers.IO){
        val resp = userRepo.getUser(id,name).await()
        if(resp.isSuccessful){
            val body = resp.body()
            userFromSingleQuery.postValue(body!!.user)
        }
    }

}