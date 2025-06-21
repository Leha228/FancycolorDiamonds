package com.example.fancycolordiamonds.api


import com.google.gson.annotations.SerializedName

data class PaymentMethod(
    @SerializedName("code")
    val code: String,
    @SerializedName("sort_order")
    val sortOrder: String,
    @SerializedName("terms")
    val terms: String,
    @SerializedName("title")
    val title: String
)