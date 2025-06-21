package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class DataShippingMethodGet(
    @SerializedName("shipping_methods")
    val shippingMethods: List<ShippingMethod>
)