package com.example.fancycolordiamonds.api


import com.google.gson.annotations.SerializedName

data class DataFavorite(
    @SerializedName("model")
    val model: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("special")
    val special: Int,
    @SerializedName("stock")
    val stock: String,
    @SerializedName("thumb")
    val thumb: String
)