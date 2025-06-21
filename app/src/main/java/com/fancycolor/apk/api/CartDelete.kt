package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class CartDelete(
    @SerializedName("error")
    val error: List<String>,
    @SerializedName("success")
    val success: Int
)