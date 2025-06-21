package com.example.fancycolordiamonds.api


import com.google.gson.annotations.SerializedName

data class ProductBacket(
    @SerializedName("data")
    val `data`: DataBacket,
    @SerializedName("error")
    val error: List<Any>,
    @SerializedName("success")
    val success: Int
)