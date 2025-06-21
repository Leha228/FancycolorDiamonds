package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class Option(
    @SerializedName("name")
    val name: String,
    @SerializedName("option_id")
    val optionId: Int,
    @SerializedName("option_value")
    val optionValue: List<OptionValue>,
    @SerializedName("product_option_id")
    val productOptionId: Int,
    @SerializedName("required")
    val required: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("value")
    val value: String
)