package com.storemaker.scanstore.network.models

import com.squareup.moshi.Json

data class Product(
    @field:Json(name = "_id")
    val id:String,
    @field:Json(name = "name")
    val name:String,
    @field:Json(name = "description")
    val description:String,
    @field:Json(name = "quantity")
    val quantity:Int,
    @field:Json(name = "price")
    val price:String,
    @field:Json(name = "category")
    val category:Category,

)