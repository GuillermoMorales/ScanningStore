package com.storemaker.scanstore.network.models

import com.squareup.moshi.Json

data class CategoryResponse (
    @field:Json(name = "categories")
    val categories:List<Category>
){
}