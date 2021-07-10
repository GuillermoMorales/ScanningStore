package com.storemaker.scanstore.repositories

import com.storemaker.scanstore.network.CategoryService
import com.storemaker.scanstore.network.ProductService
import com.storemaker.scanstore.network.models.ProductOperationResponse
import com.storemaker.scanstore.network.models.ProductResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response

class ProductRepository {
    fun getProductsAsync(): Deferred<Response<ProductResponse>> =
        ProductService.getProductService().getProductsAsync()

    fun getProductAsync(id:String): Deferred<Response<ProductOperationResponse>> =
        ProductService.getProductService().getProductAsync(id)
    fun saveProductAsync(
        name: String,
        description: String,
        quantity: Int,
        price: String,
        CategoryName: String,
    ) =
        ProductService.getProductService().saveProductAsync(
            name,
            description,
            quantity,
            price,
            CategoryName
        )
    fun updateProductAsync(
        id: String,
        name: String,
        description: String,
        quantity: Int,
        price: String,
        CategoryName: String,
    ) =
        ProductService.getProductService().updateProductAsync(
            id,
            name,
            description,
            quantity,
            price,
            CategoryName
        )
}