package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class CountryRegister(
    @SerializedName("data")
    val `data`: List<DataCountry>,
    @SerializedName("error")
    val error: List<Any>,
    @SerializedName("success")
    val success: Int
)