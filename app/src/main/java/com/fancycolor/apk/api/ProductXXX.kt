package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class ProductXXX(
    @SerializedName("href")
    val href: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("option")
    val option: List<Any>,
    @SerializedName("price")
    val price: String,
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("quantity")
    val quantity: String,
    @SerializedName("recurring")
    val recurring: String,
    @SerializedName("subtract")
    val subtract: String,
    @SerializedName("total")
    val total: String
)