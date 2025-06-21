package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class TotalX(
    @SerializedName("text")
    val text: String,
    @SerializedName("title")
    val title: String
)