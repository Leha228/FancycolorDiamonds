package com.example.fancycolordiamonds.api


import com.google.gson.annotations.SerializedName

data class DataCartAdd(
    @SerializedName("product")
    val product: ProductXX,
    @SerializedName("total")
    val total: String,
    @SerializedName("total_price")
    val totalPrice: String,
    @SerializedName("total_product_count")
    val totalProductCount: Int
)