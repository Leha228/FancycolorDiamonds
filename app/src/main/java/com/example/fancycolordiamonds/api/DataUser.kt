package com.example.fancycolordiamonds.api


import com.google.gson.annotations.SerializedName

data class DataUser(
    @SerializedName("account_custom_field")
    val accountCustomField: Any,
    @SerializedName("address_id")
    val addressId: String,
    @SerializedName("approved")
    val approved: String,
    @SerializedName("cart")
    val cart: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("custom_fields")
    val customFields: List<Any>,
    @SerializedName("customer_group_id")
    val customerGroupId: String,
    @SerializedName("customer_id")
    val customerId: String,
    @SerializedName("date_added")
    val dateAdded: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("fax")
    val fax: String,
    @SerializedName("firstname")
    val firstname: String,
    @SerializedName("ip")
    val ip: String,
    @SerializedName("language_id")
    val languageId: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("newsletter")
    val newsletter: String,
    @SerializedName("reward_total")
    val rewardTotal: String,
    @SerializedName("safe")
    val safe: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("store_id")
    val storeId: String,
    @SerializedName("telephone")
    val telephone: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("user_balance")
    val userBalance: String,
    @SerializedName("wishlist")
    val wishlist: String
)