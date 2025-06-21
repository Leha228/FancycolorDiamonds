package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class ProductView(
    @SerializedName("data")
    val `data`: DataProductView,
    @SerializedName("error")
    val error: List<Any>,
    @SerializedName("success")
    val success: Int
)