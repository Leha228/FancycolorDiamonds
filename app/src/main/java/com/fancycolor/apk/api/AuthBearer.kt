package com.fancycolor.apk.api

data class AuthBearer(
    val `data`: Data,
    val error: List<Any>,
    val success: Int
)