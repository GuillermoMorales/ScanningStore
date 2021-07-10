package com.storemaker.scanstore.repositories

import com.storemaker.scanstore.network.CartService
import com.storemaker.scanstore.network.ProductService
import com.storemaker.scanstore.network.models.CartOperationResponse
import com.storemaker.scanstore.network.models.CartResponse
import com.storemaker.scanstore.network.models.ProductResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response

class CartRepository {
    fun getCartAsync(id: String): Deferred<Response<CartOperationResponse>> =
        CartService.getCartService().getCartAsync(id)

    fun saveCartAsync(
        userId: String,
        productId: String,
        quantity: Int,
    ) =
        CartService.getCartService().saveProductAsync(
            userId,
            productId,
            quantity
        )
    fun getCartsByUserAsync(userId:String):Deferred<Response<CartResponse>> =
        CartService.getCartService().getCartsByUserAsync(userId)

    fun deleteCartAsync(userId:String):Deferred<Response<CartOperationResponse>> =
        CartService.getCartService().deleteCartAsync(userId)
}