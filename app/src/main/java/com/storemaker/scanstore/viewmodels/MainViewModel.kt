package com.storemaker.scanstore.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.storemaker.scanstore.network.models.Category
import com.storemaker.scanstore.repositories.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val categoryRepository = CategoryRepository()
    val categoryList: MutableLiveData<List<Category>> = MutableLiveData(emptyList())
    val categoryInsertedId: MutableLiveData<String> = MutableLiveData("")

    val categoryFound: MutableLiveData<Category?> = MutableLiveData(null)
    val categoryUpdated: MutableLiveData<Category?> = MutableLiveData(null)



    fun retrieveCategories() = viewModelScope.launch(Dispatchers.IO) {
        val resp = categoryRepository.getCategoriesAsync().await()
        if (resp.isSuccessful) {
            val body = resp.body()
            categoryList.postValue(body!!.categories)
        }
    }

    fun retrieveCategory(id: String) = viewModelScope.launch(Dispatchers.IO) {
        val resp = categoryRepository.getCategoryAsync(id).await()
        if (resp.isSuccessful) {
            val body = resp.body()
            categoryFound.postValue(body!!.category)
        }
    }

    fun saveCategory(name: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val resp = categoryRepository.saveCategoryAsync(name).await()
            if (resp.isSuccessful) {
                val body = resp.body()
                categoryInsertedId.postValue(body!!.category.id)
            }

        }

    fun updateCategory(id: String, name: String) = viewModelScope.launch(Dispatchers.IO) {
        val resp = categoryRepository.updateCategoryAsync(id,name).await()
        if (resp.isSuccessful) {
            val body = resp.body()
            categoryUpdated.postValue(body!!.category)
        }

    }
}