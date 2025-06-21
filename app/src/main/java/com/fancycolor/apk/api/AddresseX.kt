package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class AddresseX(
    @SerializedName("address_1")
    val address1: String,
    @SerializedName("address_2")
    val address2: String,
    @SerializedName("address_format")
    val addressFormat: String,
    @SerializedName("address_id")
    val addressId: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("company")
    val company: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("country_id")
    val countryId: String,
    @SerializedName("custom_field")
    val customField: Any,
    @SerializedName("firstname")
    val firstname: String,
    @SerializedName("iso_code_2")
    val isoCode2: String,
    @SerializedName("iso_code_3")
    val isoCode3: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("postcode")
    val postcode: String,
    @SerializedName("zone")
    val zone: String,
    @SerializedName("zone_code")
    val zoneCode: String,
    @SerializedName("zone_id")
    val zoneId: String
)