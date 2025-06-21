package com.example.fancycolordiamonds.api


import com.google.gson.annotations.SerializedName

data class RegisterAccount(
    @SerializedName("data")
    val `data`: DataRegisterAccount,
    @SerializedName("error")
    val error: List<Any>,
    @SerializedName("success")
    val success: Int
)