package com.example.fancycolordiamonds.api


import com.google.gson.annotations.SerializedName

data class DataCountry(
    @SerializedName("address_format")
    val addressFormat: String,
    @SerializedName("country_id")
    val countryId: Int,
    @SerializedName("iso_code_2")
    val isoCode2: String,
    @SerializedName("iso_code_3")
    val isoCode3: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("postcode_required")
    val postcodeRequired: String,
    @SerializedName("status")
    val status: String
)