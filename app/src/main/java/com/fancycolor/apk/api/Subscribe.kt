package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class Subscribe(
    @SerializedName("data")
    val `data`: List<Any>,
    @SerializedName("error")
    val error: List<Any>,
    @SerializedName("success")
    val success: Int
)