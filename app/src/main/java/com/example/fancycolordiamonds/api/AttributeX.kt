package com.example.fancycolordiamonds.api


import com.google.gson.annotations.SerializedName

data class AttributeX(
    @SerializedName("attribute_id")
    val attributeId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("text")
    val text: String
)