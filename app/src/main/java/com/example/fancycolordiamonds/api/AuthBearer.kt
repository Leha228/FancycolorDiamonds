package com.example.fancycolordiamonds.api

data class AuthBearer(
    val `data`: Data,
    val error: List<Any>,
    val success: Int
)