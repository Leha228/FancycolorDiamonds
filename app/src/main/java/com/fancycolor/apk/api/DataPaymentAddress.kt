package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class DataPaymentAddress(
    @SerializedName("address_id")
    val addressId: String,
    @SerializedName("addresses")
    val addresses: List<Addresse>
)