package com.storemaker.scanstore.repositories

import com.storemaker.scanstore.network.CategoryService
import com.storemaker.scanstore.network.models.CategoryOperationResponse
import com.storemaker.scanstore.network.models.CategoryResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response

class CategoryRepository {
     fun getCategoriesAsync(): Deferred<Response<CategoryResponse>> =
        CategoryService.getCategoryService().getCategoriesAsync()

     fun saveCategoryAsync(name: String) =
        CategoryService.getCategoryService().saveCategoryAsync(name)

    fun updateCategoryAsync(id:String,name: String) =
        CategoryService.getCategoryService().updateCategoryAsync(id,name)

    fun getCategoryAsync(id:String): Deferred<Response<CategoryOperationResponse>> =
        CategoryService.getCategoryService().getCategoryAsync(id)
}