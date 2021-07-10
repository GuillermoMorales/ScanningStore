package com.storemaker.scanstore.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.storemaker.scanstore.Constants
import com.storemaker.scanstore.network.models.CategoryOperationResponse
import com.storemaker.scanstore.network.models.CategoryResponse
import com.storemaker.scanstore.network.models.SingleUserResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface CategoryService {

    @GET("/categories")
    fun getCategoriesAsync(): Deferred<Response<CategoryResponse>>

    @GET("/categories/{id}")
    fun getCategoryAsync(@Path(value = "id") id:String): Deferred<Response<CategoryOperationResponse>>

    @FormUrlEncoded
    @POST("/categories")
    fun saveCategoryAsync(
        @Field("name")
        name: String,
    ): Deferred<Response<CategoryOperationResponse>>

    @FormUrlEncoded
    @PUT("/categories/{id}")
    fun updateCategoryAsync(
        @Path("id")
        id: String,
        @Field("name")
        name: String
    ): Deferred<Response<CategoryOperationResponse>>

    companion object{
        fun getCategoryService():CategoryService = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)

            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(CategoryService::class.java)
    }
}