package com.storemaker.scanstore.repositories

import com.storemaker.scanstore.network.UserService
import com.storemaker.scanstore.network.models.SingleUserResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response

class UserRepository {
    fun getUser(
        id: String,
        name:String
    ): Deferred<Response<SingleUserResponse>> =
        UserService.getUserService().getUserAsync(id,name)
}