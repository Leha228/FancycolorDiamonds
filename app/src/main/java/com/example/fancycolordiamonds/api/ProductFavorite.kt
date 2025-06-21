package com.example.fancycolordiamonds.api


import com.google.gson.annotations.SerializedName

data class ProductFavorite(
    @SerializedName("data")
    val `data`: List<DataFavorite>,
    @SerializedName("error")
    val error: List<Any>,
    @SerializedName("success")
    val success: Int
)