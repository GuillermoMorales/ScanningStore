package com.storemaker.scanstore.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.storemaker.scanstore.Constants
import com.storemaker.scanstore.network.models.SingleUserResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface UserService {
    @POST("/users/email")
    @FormUrlEncoded
    fun getUserAsync(
        @Field(value = "email")
        id:String,
        @Field(value = "name")
        name:String
    ):Deferred<Response<SingleUserResponse>>



    companion object{
        fun getUserService():UserService = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)

            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(UserService::class.java)
    }
}