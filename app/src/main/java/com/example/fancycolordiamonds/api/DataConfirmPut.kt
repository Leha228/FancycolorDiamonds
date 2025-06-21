package com.example.fancycolordiamonds.api


import com.google.gson.annotations.SerializedName

data class DataConfirmPut(
    @SerializedName("order_id")
    val orderId: Int
)