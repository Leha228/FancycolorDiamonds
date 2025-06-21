package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class Total(
    @SerializedName("text")
    val text: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("value")
    val value: Int
)