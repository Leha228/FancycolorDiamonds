package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class FilterGroup(
    @SerializedName("filter")
    val filter: List<Filter>,
    @SerializedName("filter_group_id")
    val filterGroupId: String,
    @SerializedName("name")
    val name: String
)