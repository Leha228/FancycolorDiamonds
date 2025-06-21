package com.example.fancycolordiamonds.api


import com.google.gson.annotations.SerializedName

data class AttributeGroup(
    @SerializedName("attribute")
    val attribute: List<Attribute>,
    @SerializedName("attribute_group_id")
    val attributeGroupId: String,
    @SerializedName("name")
    val name: String
)