package com.example.fancycolordiamonds.api


import com.google.gson.annotations.SerializedName

data class ShippingAddress(
    @SerializedName("data")
    val `data`: DataShippingAddress,
    @SerializedName("error")
    val error: List<Any>,
    @SerializedName("success")
    val success: Int
)