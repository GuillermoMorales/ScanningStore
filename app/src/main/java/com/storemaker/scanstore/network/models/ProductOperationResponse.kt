package com.storemaker.scanstore.network.models

import com.squareup.moshi.Json

data class ProductOperationResponse(
    @field:Json(name = "product")
    val product:Product
)