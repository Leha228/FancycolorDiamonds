package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class PaymentMethodGet(
    @SerializedName("data")
    val `data`: DataPaymentMethodGet,
    @SerializedName("error")
    val error: List<Any>,
    @SerializedName("success")
    val success: Int
)