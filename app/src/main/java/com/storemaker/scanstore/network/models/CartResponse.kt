package com.storemaker.scanstore.network.models

import com.squareup.moshi.Json

data class CartResponse(
    @field:Json(name = "carts")
    val carts:List<Cart>
)