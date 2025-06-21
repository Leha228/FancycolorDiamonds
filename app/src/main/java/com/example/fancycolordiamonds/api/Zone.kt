package com.example.fancycolordiamonds.api


import com.google.gson.annotations.SerializedName

data class Zone(
    @SerializedName("code")
    val code: String,
    @SerializedName("country_id")
    val countryId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("zone_id")
    val zoneId: String
)