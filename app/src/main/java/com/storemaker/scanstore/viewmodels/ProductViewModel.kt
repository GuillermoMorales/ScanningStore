package com.storemaker.scanstore.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.storemaker.scanstore.network.models.Category
import com.storemaker.scanstore.network.models.Product
import com.storemaker.scanstore.repositories.CategoryRepository
import com.storemaker.scanstore.repositories.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val productRepo = ProductRepository()
    private val categoryRepo = CategoryRepository()

    val productList: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val categoryList: MutableLiveData<List<Category>> = MutableLiveData(emptyList())

    val productInsertedId: MutableLiveData<String> = MutableLiveData("")

    val productFound: MutableLiveData<Product?> = MutableLiveData(null)
    val productUpdated: MutableLiveData<Product?> = MutableLiveData(null)


    fun retrieveCategories() = viewModelScope.launch(Dispatchers.IO) {
        val resp = categoryRepo.getCategoriesAsync().await()
        if (resp.isSuccessful) {
            val body = resp.body()
            categoryList.postValue(body!!.categories)
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

    fun saveProduct(
        name: String,
        description: String,
        quantity: Int,
        price: String,
        CategoryName: String
    ) =
        viewModelScope.launch(Dispatchers.IO) {
            val resp = productRepo.saveProductAsync(
                name,
                description,
                quantity,
                price,
                CategoryName
            ).await()
            if (resp.isSuccessful) {
                val body = resp.body()
                productInsertedId.postValue(body!!.product.id)
            }
            else{
                Log.d("CUSTOM",resp.message())
            }

        }

    fun updateProduct(
        id: String,
        name: String,
        description: String,
        quantity: Int,
        price: String,
        CategoryName: String
    ) =
        viewModelScope.launch(Dispatchers.IO) {
            val resp = productRepo.updateProductAsync(
                id,
                name,
                description,
                quantity,
                price,
                CategoryName
            ).await()
            if (resp.isSuccessful) {
                val body = resp.body()
                productUpdated.postValue(body!!.product)
            }

        }

}