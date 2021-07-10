package com.storemaker.scanstore.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.storemaker.scanstore.Constants
import com.storemaker.scanstore.network.models.ProductOperationResponse
import com.storemaker.scanstore.network.models.ProductResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


interface ProductService {

    @GET("/products")
    fun getProductsAsync(): Deferred<Response<ProductResponse>>

    @GET("/products/{id}")
    fun getProductAsync(@Path(value = "id") id:String): Deferred<Response<ProductOperationResponse>>

    @FormUrlEncoded
    @POST("/products")
    fun saveProductAsync(
        @Field("name")
        name: String,
        @Field("description")
        description: String,
        @Field("quantity")
        quantity: Int,
        @Field("price")
        price: String,
        @Field("categoryName")
        categoryName: String,
    ): Deferred<Response<ProductOperationResponse>>

    @FormUrlEncoded
    @PUT("/products/{id}")
    fun updateProductAsync(
        @Path("id")
        id: String,
        @Field("name")
        name: String,
        @Field("description")
        description: String,
        @Field("quantity")
        quantity: Int,
        @Field("price")
        price: String,
        @Field("categoryName")
        CategoryName: String,
    ): Deferred<Response<ProductOperationResponse>>

    companion object{
        fun getProductService():ProductService = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(ProductService::class.java)
    }
}