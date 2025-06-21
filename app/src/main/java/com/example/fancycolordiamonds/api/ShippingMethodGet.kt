package com.example.fancycolordiamonds.api


import com.google.gson.annotations.SerializedName

data class ShippingMethodGet(
    @SerializedName("data")
    val `data`: DataShippingMethodGet,
    @SerializedName("error")
    val error: List<Any>,
    @SerializedName("success")
    val success: Int
)