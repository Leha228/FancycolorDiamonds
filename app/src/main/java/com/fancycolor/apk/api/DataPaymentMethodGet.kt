package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class DataPaymentMethodGet(
    @SerializedName("payment_methods")
    val paymentMethods: List<PaymentMethod>
)