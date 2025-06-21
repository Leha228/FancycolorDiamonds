package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class DataConfirmPut(
    @SerializedName("order_id")
    val orderId: Int
)