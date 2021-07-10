package com.storemaker.scanstore.network.models

import com.squareup.moshi.Json

data class CategoryOperationResponse (
    @field:Json(name = "category")
    val category:Category
)