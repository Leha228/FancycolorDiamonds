package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class Attribute(
    @SerializedName("attribute_id")
    val attributeId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("text")
    val text: String
)