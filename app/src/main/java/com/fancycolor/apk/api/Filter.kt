package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class Filter(
    @SerializedName("filter_id")
    val filterId: String,
    @SerializedName("name")
    val name: String
)