package com.storemaker.scanstore.network.models

import com.squareup.moshi.Json

data class Users(
    @field:Json(name = "_id")
    val id:String,
    @field:Json(name = "name")
    val name:String,
    @field:Json(name = "email")
    val email:String,
    @field:Json(name = "role")
    val role:String,
) {
}