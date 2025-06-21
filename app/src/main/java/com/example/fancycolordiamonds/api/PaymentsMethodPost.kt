package com.example.fancycolordiamonds.api


import com.google.gson.annotations.SerializedName

data class PaymentsMethodPost(
    @SerializedName("data")
    val `data`: List<Any>,
    @SerializedName("error")
    val error: List<Any>,
    @SerializedName("success")
    val success: Int
)