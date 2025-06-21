package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class Filters(
    @SerializedName("filter_groups")
    val filterGroups: List<FilterGroup>
)