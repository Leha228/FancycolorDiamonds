package com.example.fancycolordiamonds.api


import com.google.gson.annotations.SerializedName

data class DataX(
    @SerializedName("attribute_groups")
    val attributeGroups: List<AttributeGroup>,
    @SerializedName("category")
    val category: List<Category>,
    @SerializedName("date_added")
    val dateAdded: String,
    @SerializedName("date_available")
    val dateAvailable: String,
    @SerializedName("date_modified")
    val dateModified: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("discounts")
    val discounts: List<Any>,
    @SerializedName("ean")
    val ean: String,
    @SerializedName("height")
    val height: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("isbn")
    val isbn: String,
    @SerializedName("jan")
    val jan: String,
    @SerializedName("length")
    val length: String,
    @SerializedName("length_class")
    val lengthClass: String,
    @SerializedName("length_class_id")
    val lengthClassId: Int,
    @SerializedName("location")
    val location: String,
    @SerializedName("manufacturer")
    val manufacturer: String,
    @SerializedName("manufacturer_id")
    val manufacturerId: Int,
    @SerializedName("meta_description")
    val metaDescription: String,
    @SerializedName("meta_keyword")
    val metaKeyword: String,
    @SerializedName("meta_title")
    val metaTitle: String,
    @SerializedName("minimum")
    val minimum: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("mpn")
    val mpn: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("options")
    val options: List<Any>,
    @SerializedName("original_image")
    val originalImage: String,
    @SerializedName("original_images")
    val originalImages: List<String>,
    @SerializedName("points")
    val points: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("price_formated")
    val priceFormated: String,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("recurrings")
    val recurrings: List<Any>,
    @SerializedName("reviews")
    val reviews: Reviews,
    @SerializedName("reward")
    val reward: Any,
    @SerializedName("shipping")
    val shipping: String,
    @SerializedName("sku")
    val sku: String,
    @SerializedName("sort_order")
    val sortOrder: Int,
    @SerializedName("special")
    val special: Int,
    @SerializedName("special_end_date")
    val specialEndDate: String,
    @SerializedName("special_excluding_tax")
    val specialExcludingTax: Int,
    @SerializedName("special_excluding_tax_formated")
    val specialExcludingTaxFormated: String,
    @SerializedName("special_formated")
    val specialFormated: String,
    @SerializedName("special_start_date")
    val specialStartDate: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("stock_status")
    val stockStatus: String,
    @SerializedName("stock_status_id")
    val stockStatusId: Int,
    @SerializedName("subtract")
    val subtract: String,
    @SerializedName("tag")
    val tag: String,
    @SerializedName("tax_class_id")
    val taxClassId: Int,
    @SerializedName("upc")
    val upc: String,
    @SerializedName("viewed")
    val viewed: String,
    @SerializedName("weight")
    val weight: String,
    @SerializedName("weight_class")
    val weightClass: String,
    @SerializedName("weight_class_id")
    val weightClassId: Int,
    @SerializedName("width")
    val width: String
)