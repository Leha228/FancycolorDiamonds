package com.example.fancycolordiamonds.api


import com.google.gson.annotations.SerializedName

data class ShippingMethodPost(
    @SerializedName("data")
    val `data`: List<Any>,
    @SerializedName("error")
    val error: List<Any>,
    @SerializedName("success")
    val success: Int
)