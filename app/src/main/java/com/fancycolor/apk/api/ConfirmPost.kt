package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class ConfirmPost(
    @SerializedName("data")
    val `data`: DataConfirmPost,
    @SerializedName("error")
    val error: List<Any>,
    @SerializedName("success")
    val success: Int
)