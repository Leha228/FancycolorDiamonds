package com.example.fancycolordiamonds.api


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("data")
    val `data`: List<DataX>,
    @SerializedName("error")
    val error: List<Any>,
    @SerializedName("success")
    val success: Int
)