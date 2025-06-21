package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class DataRegisterAccount(
    @SerializedName("address_1")
    val address1: String,
    @SerializedName("address_id")
    val addressId: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("company")
    val company: String,
    @SerializedName("company_id")
    val companyId: String,
    @SerializedName("country_id")
    val countryId: String,
    @SerializedName("customer_id")
    val customerId: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("fax")
    val fax: String,
    @SerializedName("firstname")
    val firstname: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("tax_id")
    val taxId: Int,
    @SerializedName("telephone")
    val telephone: String,
    @SerializedName("zone_id")
    val zoneId: String,
    @SerializedName("postcode")
    val postcode: String
)