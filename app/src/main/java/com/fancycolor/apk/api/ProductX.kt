package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class ProductX(
    @SerializedName("key")
    val key: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("option")
    val option: List<Any>,
    @SerializedName("points")
    val points: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("quantity")
    val quantity: String,
    @SerializedName("recurring")
    val recurring: String,
    @SerializedName("reward")
    val reward: String,
    @SerializedName("stock")
    val stock: Boolean,
    @SerializedName("thumb")
    val thumb: String,
    @SerializedName("total")
    val total: String
)