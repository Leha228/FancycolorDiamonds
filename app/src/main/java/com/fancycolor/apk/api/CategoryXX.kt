package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class CategoryXX(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)