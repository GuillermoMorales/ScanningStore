package com.storemaker.scanstore.network.models

import com.squareup.moshi.Json

data class Category(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name = "name")
    val name: String
)