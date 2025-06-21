package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class OptionValue(
    @SerializedName("image")
    val image: Any,
    @SerializedName("name")
    val name: String,
    @SerializedName("option_value_id")
    val optionValueId: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("price_excluding_tax")
    val priceExcludingTax: Int,
    @SerializedName("price_excluding_tax_formated")
    val priceExcludingTaxFormated: String,
    @SerializedName("price_formated")
    val priceFormated: String,
    @SerializedName("price_prefix")
    val pricePrefix: String,
    @SerializedName("product_option_value_id")
    val productOptionValueId: Int,
    @SerializedName("quantity")
    val quantity: String
)