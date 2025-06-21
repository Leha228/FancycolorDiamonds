package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class DataUserLogin(
    @SerializedName("account_custom_field")
    val accountCustomField: Any,
    @SerializedName("address_id")
    val addressId: String,
    @SerializedName("approved")
    val approved: String,
    @SerializedName("cart")
    val cart: String,
    @SerializedName("cart_count_products")
    val cartCountProducts: Int,
    @SerializedName("code")
    val code: String,
    @SerializedName("custom_field")
    val customField: String,
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
    @SerializedName("safe")
    val safe: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("store_id")
    val storeId: String,
    @SerializedName("telephone")
    val telephone: String,
    @SerializedName("wishlist")
    val wishlist: List<Any>,
    @SerializedName("wishlist_total")
    val wishlistTotal: String
)