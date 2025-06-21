package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class FilterProductRun(
    @SerializedName("data")
    val `data`: List<DataFilterProductRun>,
    @SerializedName("error")
    val error: List<Any>,
    @SerializedName("success")
    val success: Int
)