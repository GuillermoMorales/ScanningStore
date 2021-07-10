package com.storemaker.scanstore.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.storemaker.scanstore.Constants
import com.storemaker.scanstore.network.models.CartOperationResponse
import com.storemaker.scanstore.network.models.CartResponse
import com.storemaker.scanstore.network.models.ProductOperationResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface CartService {

    @GET("/carts/{id}")
    fun getCartAsync(@Path(value = "id") id: String): Deferred<Response<CartOperationResponse>>

    @FormUrlEncoded
    @POST("/carts")
    fun saveProductAsync(
        @Field("userId")
        userId: String,
        @Field("productId")
        productId: String,
        @Field("quantity")
        quantity: Int,

        ): Deferred<Response<CartOperationResponse>>

    @FormUrlEncoded
    @POST("/carts/user")
    fun getCartsByUserAsync(
        @Field("userId")
        userId: String
    ): Deferred<Response<CartResponse>>

    @DELETE("/carts/{id}")
    fun deleteCartAsync(@Path(value = "id") id: String): Deferred<Response<CartOperationResponse>>


    companion object{
        fun getCartService():CartService = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(CartService::class.java)
    }
}