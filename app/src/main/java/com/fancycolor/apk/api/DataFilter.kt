package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class DataFilter(
    @SerializedName("description")
    val description: String,
    @SerializedName("filters")
    val filters: Filters,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("original_image")
    val originalImage: String,
    @SerializedName("sub_categories")
    val subCategories: List<Any>
)