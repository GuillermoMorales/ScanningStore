package com.storemaker.scanstore.network.models

import com.squareup.moshi.Json

data class SingleUserResponse(
    @field:Json(name = "user")
    val user:Users
) {
}