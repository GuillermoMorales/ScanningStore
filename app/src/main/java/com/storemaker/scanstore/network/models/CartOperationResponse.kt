package com.storemaker.scanstore.network.models

import com.squareup.moshi.Json

data class CartOperationResponse (
    @field:Json(name = "cart")
    val cart:Cart
)