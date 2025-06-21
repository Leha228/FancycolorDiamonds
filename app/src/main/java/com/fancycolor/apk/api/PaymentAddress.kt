package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class PaymentAddress(
    @SerializedName("data")
    val `data`: DataPaymentAddress,
    @SerializedName("error")
    val error: List<Any>,
    @SerializedName("success")
    val success: Int
)