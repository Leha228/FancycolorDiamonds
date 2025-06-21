package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class Currency(
    @SerializedName("currency_id")
    val currencyId: String,
    @SerializedName("decimal_place")
    val decimalPlace: String,
    @SerializedName("symbol_left")
    val symbolLeft: String,
    @SerializedName("symbol_right")
    val symbolRight: String,
    @SerializedName("value")
    val value: String
)