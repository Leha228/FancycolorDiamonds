package com.example.fancycolordiamonds.api


import com.google.gson.annotations.SerializedName

data class DataPaymentMethodGet(
    @SerializedName("payment_methods")
    val paymentMethods: List<PaymentMethod>
)