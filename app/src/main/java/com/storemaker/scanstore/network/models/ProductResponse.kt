package com.storemaker.scanstore.network.models

import com.squareup.moshi.Json

data class ProductResponse(
    @field:Json(name = "products")
    val products:List<Product>
)