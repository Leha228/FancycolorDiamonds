package com.fancycolor.apk.api


import com.google.gson.annotations.SerializedName

data class DataBacket(
    @SerializedName("coupon")
    val coupon: String,
    @SerializedName("coupon_status")
    val couponStatus: Any,
    @SerializedName("currency")
    val currency: Currency,
    @SerializedName("has_download")
    val hasDownload: Int,
    @SerializedName("has_recurring_products")
    val hasRecurringProducts: Int,
    @SerializedName("has_shipping")
    val hasShipping: Int,
    @SerializedName("products")
    val products: List<ProductX>,
    @SerializedName("reward")
    val reward: String,
    @SerializedName("reward_status")
    val rewardStatus: Boolean,
    @SerializedName("total")
    val total: String,
    @SerializedName("total_product_count")
    val totalProductCount: Int,
    @SerializedName("voucher")
    val voucher: String,
    @SerializedName("voucher_status")
    val voucherStatus: Any,
    @SerializedName("vouchers")
    val vouchers: List<Any>,
    @SerializedName("weight")
    val weight: String
)