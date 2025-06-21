package com.example.fancycolordiamonds.api


import com.google.gson.annotations.SerializedName

data class AttributeGroupX(
    @SerializedName("attribute")
    val attribute: List<AttributeX>,
    @SerializedName("attribute_group_id")
    val attributeGroupId: String,
    @SerializedName("name")
    val name: String
)