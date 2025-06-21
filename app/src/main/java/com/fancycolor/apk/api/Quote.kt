package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class Quote(
    @SerializedName("code")
    val code: String,
    @SerializedName("cost")
    val cost: String,
    @SerializedName("tax_class_id")
    val taxClassId: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("title")
    val title: String
)