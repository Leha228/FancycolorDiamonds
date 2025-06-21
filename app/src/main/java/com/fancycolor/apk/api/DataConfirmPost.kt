package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class DataConfirmPost(
    @SerializedName("accept_language")
    val acceptLanguage: String,
    @SerializedName("affiliate_id")
    val affiliateId: Int,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("commission")
    val commission: Int,
    @SerializedName("currency_code")
    val currencyCode: String,
    @SerializedName("currency_id")
    val currencyId: String,
    @SerializedName("currency_value")
    val currencyValue: String,
    @SerializedName("custom_field")
    val customField: Any,
    @SerializedName("customer_group_id")
    val customerGroupId: String,
    @SerializedName("customer_id")
    val customerId: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("fax")
    val fax: String,
    @SerializedName("firstname")
    val firstname: String,
    @SerializedName("forwarded_ip")
    val forwardedIp: String,
    @SerializedName("invoice_prefix")
    val invoicePrefix: String,
    @SerializedName("ip")
    val ip: String,
    @SerializedName("language_id")
    val languageId: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("marketing_id")
    val marketingId: Int,
    @SerializedName("order_id")
    val orderId: Int,
    @SerializedName("payment")
    val payment: String,
    @SerializedName("payment_address_1")
    val paymentAddress1: String,
    @SerializedName("payment_address_2")
    val paymentAddress2: String,
    @SerializedName("payment_address_format")
    val paymentAddressFormat: String,
    @SerializedName("payment_city")
    val paymentCity: String,
    @SerializedName("payment_code")
    val paymentCode: String,
    @SerializedName("payment_company")
    val paymentCompany: String,
    @SerializedName("payment_country")
    val paymentCountry: String,
    @SerializedName("payment_country_id")
    val paymentCountryId: String,
    @SerializedName("payment_custom_field")
    val paymentCustomField: Any,
    @SerializedName("payment_firstname")
    val paymentFirstname: String,
    @SerializedName("payment_lastname")
    val paymentLastname: String,
    @SerializedName("payment_method")
    val paymentMethod: String,
    @SerializedName("payment_postcode")
    val paymentPostcode: String,
    @SerializedName("payment_zone")
    val paymentZone: String,
    @SerializedName("payment_zone_id")
    val paymentZoneId: String,
    @SerializedName("products")
    val products: List<ProductXXX>,
    @SerializedName("shipping_address_1")
    val shippingAddress1: String,
    @SerializedName("shipping_address_2")
    val shippingAddress2: String,
    @SerializedName("shipping_address_format")
    val shippingAddressFormat: String,
    @SerializedName("shipping_city")
    val shippingCity: String,
    @SerializedName("shipping_code")
    val shippingCode: String,
    @SerializedName("shipping_company")
    val shippingCompany: String,
    @SerializedName("shipping_country")
    val shippingCountry: String,
    @SerializedName("shipping_country_id")
    val shippingCountryId: String,
    @SerializedName("shipping_custom_field")
    val shippingCustomField: List<Any>,
    @SerializedName("shipping_firstname")
    val shippingFirstname: String,
    @SerializedName("shipping_lastname")
    val shippingLastname: String,
    @SerializedName("shipping_method")
    val shippingMethod: String,
    @SerializedName("shipping_postcode")
    val shippingPostcode: String,
    @SerializedName("shipping_zone")
    val shippingZone: String,
    @SerializedName("shipping_zone_id")
    val shippingZoneId: String,
    @SerializedName("store_id")
    val storeId: Int,
    @SerializedName("store_name")
    val storeName: String,
    @SerializedName("store_url")
    val storeUrl: String,
    @SerializedName("telephone")
    val telephone: String,
    @SerializedName("total")
    val total: Double,
    @SerializedName("totals")
    val totals: List<TotalX>,
    @SerializedName("tracking")
    val tracking: String,
    @SerializedName("user_agent")
    val userAgent: String,
    @SerializedName("vouchers")
    val vouchers: List<Any>
)