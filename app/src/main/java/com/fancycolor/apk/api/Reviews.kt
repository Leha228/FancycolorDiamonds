package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class Reviews(
    @SerializedName("review_total")
    val reviewTotal: String
)