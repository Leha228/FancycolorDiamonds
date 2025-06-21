package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class AuthUser(
    @SerializedName("data")
    val `data`: DataUser,
    @SerializedName("error")
    val error: List<Any>,
    @SerializedName("success")
    val success: Int
)