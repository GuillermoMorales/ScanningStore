package com.storemaker.scanstore.network.models

import com.squareup.moshi.Json

data class Cart (
    @field:Json(name = "id")
    val id:String,
    @field:Json(name = "product")
    val product:Product,
    @field:Json(name = "user")
    val users: Users,
    @field:Json(name = "quantity")
    val quantity:Int
)