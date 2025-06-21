package com.example.fancycolordiamonds.api


import com.google.gson.annotations.SerializedName

data class Currate(
    @SerializedName("data")
    val `data`: DataCurrate,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)