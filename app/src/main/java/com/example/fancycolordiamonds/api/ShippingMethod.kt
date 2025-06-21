package com.example.fancycolordiamonds.api


import com.google.gson.annotations.SerializedName

data class ShippingMethod(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("quote")
    val quote: List<Quote>,
    @SerializedName("sort_order")
    val sortOrder: String,
    @SerializedName("title")
    val title: String
)