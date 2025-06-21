package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class ProductXX(
    @SerializedName("name")
    val name: String,
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("quantity")
    val quantity: String
)