package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class AttributeGroupXX(
    @SerializedName("attribute")
    val attribute: List<AttributeXX>,
    @SerializedName("attribute_group_id")
    val attributeGroupId: String,
    @SerializedName("name")
    val name: String
)