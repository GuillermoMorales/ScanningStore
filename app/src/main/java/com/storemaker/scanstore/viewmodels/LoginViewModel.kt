package com.storemaker.scanstore.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.storemaker.scanstore.network.models.Users
import com.storemaker.scanstore.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel:ViewModel(){
    private val userRepository = UserRepository()
    val userFromSingleQuery:MutableLiveData<Users?> = MutableLiveData(null)

    fun retrieveUser(id:String,name:String) = viewModelScope.launch (Dispatchers.IO){
        val resp = userRepository.getUser(id,name).await()
        if(resp.isSuccessful){
            val body = resp.body()
            userFromSingleQuery.postValue(body!!.user)
        }
    }
}