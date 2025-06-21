package com.example.fancycolordiamonds.api


import com.google.gson.annotations.SerializedName

data class ConfirmPut(
    @SerializedName("data")
    val `data`: DataConfirmPut,
    @SerializedName("error")
    val error: List<Any>,
    @SerializedName("success")
    val success: Int
)