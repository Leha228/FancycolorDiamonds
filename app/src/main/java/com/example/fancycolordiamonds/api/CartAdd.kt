package com.example.fancycolordiamonds.api


import com.google.gson.annotations.SerializedName

data class CartAdd(
    @SerializedName("data")
    val `data`: DataCartAdd,
    @SerializedName("error")
    val error: List<Any>,
    @SerializedName("success")
    val success: Int
)