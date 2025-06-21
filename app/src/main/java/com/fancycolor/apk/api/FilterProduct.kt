package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class FilterProduct(
    @SerializedName("data")
    val `data`: DataFilter,
    @SerializedName("error")
    val error: List<Any>,
    @SerializedName("success")
    val success: Int
)