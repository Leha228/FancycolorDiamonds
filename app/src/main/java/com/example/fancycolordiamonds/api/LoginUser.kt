package com.example.fancycolordiamonds.api


import com.google.gson.annotations.SerializedName

data class LoginUser(
    @SerializedName("data")
    val `data`: DataUserLogin,
    @SerializedName("error")
    val error: List<Any>,
    @SerializedName("success")
    val success: Int
)