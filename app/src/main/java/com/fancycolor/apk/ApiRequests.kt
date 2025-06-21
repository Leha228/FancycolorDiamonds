package com.fancycolor.apk

import com.fancycolor.apk.api.*
import retrofit2.Call
import retrofit2.http.*

interface ApiRequests {

    @Headers("Authorization: Basic c2hvcHBpbmdfb2F1dGhfY2xpZW50OnNob3BwaW5nX29hdXRoX3NlY3JldA==", "Content-Type: application/json")
    @POST("/index.php?route=feed/rest_api/gettoken&grant_type=client_credentials")
    fun getAuthBearer(): Call<AuthBearer>

    @Headers("Authorization: Basic c2hvcHBpbmdfb2F1dGhfY2xpZW50OnNob3BwaW5nX29hdXRoX3NlY3JldA==", "Content-Type: application/json")
    @POST("/index.php?route=feed/rest_api/gettoken&grant_type=client_credentials")
    fun getAuthBearerOldToken(@Body map: Map<String, String>): Call<AuthBearer>

    @POST("/index.php?route=rest/forgotten/forgotten")
    fun refreshPassword(@Header("Authorization") bearer:String, @Body map: Map<String, String>): Call<RefreshPassword>

    @GET("/index.php?route=feed/rest_api/products&limit=50")
    fun getProductAll(@Header("Authorization") bearer:String, @Query("category") category:Int, @Query("filters") filters:String, @Query("page") page: Int): Call<Product>

    @GET("/index.php?route=feed/rest_api/categories&limit=50")
    fun getProductFilter(@Header("Authorization") bearer:String, @Query("id") category:Int, @Query("page") page: Int): Call<FilterProduct>

    @GET("/index.php?route=feed/rest_api/products&limit=50")
    fun getProductSort(@Header("Authorization") bearer:String, @Query("category") category:Int, @Query("sort") key:String, @Query("order") value:String,  @Query("filters") filters:String, @Query("page") page: Int): Call<Product>

    @GET("/index.php?route=rest/account/account")
    fun getAuthUser(@Header("Authorization") bearer:String): Call<AuthUser>

    @PUT("/index.php?route=rest/account/account")
    fun getAuthNewData(@Header("Authorization") bearer:String, @Body map: Map<String, String>): Call<RenameProfileData>

    @PUT("/index.php?route=rest/account/password")
    fun getAuthNewPassword(@Header("Authorization") bearer:String, @Body map: Map<String, String>): Call<RenamePassword>

    @POST("/index.php?route=rest/login/login")
    fun login(@Header("Authorization") bearer:String, @Body map: Map<String, String>): Call<LoginUser>

    @POST("/index.php?route=rest/logout/logout")
    fun logout(@Header("Authorization") bearer:String): Call<LogoutUser>

    @GET("/index.php?route=rest/wishlist/wishlist")
    fun getProductFavorite(@Header("Authorization") bearer:String): Call<ProductFavorite>

    @GET("/index.php?route=rest/cart/cart")
    fun getProductBacket(@Header("Authorization") bearer:String): Call<ProductBacket>

    @GET("/index.php?route=feed/rest_api/products")
    fun getProductView(@Header("Authorization") bearer:String, @Query("id") id:Int): Call<ProductView>

    @DELETE("/index.php?route=rest/cart/cart")
    fun cartDelete(@Header("Authorization") bearer:String, @Query("key") key:String): Call<CartDelete>

    @POST("/index.php?route=rest/cart/cart")
    fun cartAdd(@Header("Authorization") bearer:String, @Body map: Map<String, String>): Call<CartAdd>

    @POST("/index.php?route=rest/wishlist/wishlist")
    fun favoriteAdd(@Header("Authorization") bearer:String, @Query("id") id:Int): Call<FavoriteAdd>

    @POST("/index.php?route=rest/register/register")
    fun registerAccount(@Header("Authorization") bearer:String, @Body map: Map<String, String>): Call<RegisterAccount>

    @GET("/index.php?route=feed/rest_api/countries")
    fun getCountry(@Header("Authorization") bearer:String): Call<CountryRegister>

    @GET("/index.php?route=feed/rest_api/countries")
    fun getRegion(@Header("Authorization") bearer:String, @Query("id") id:Int): Call<RegionRegister>

    @GET("/index.php?route=rest/payment_address/paymentaddress")
    fun getPaymentAddress(@Header("Authorization") bearer:String): Call<PaymentAddress>

    @POST("/index.php?route=rest/payment_address/paymentaddress&existing=1")
    fun getPaymentAddressPost(@Header("Authorization") bearer:String, @Body map: Map<String, String>): Call<PaymentAddressPost>

    @GET("/index.php?route=rest/shipping_address/shippingaddress")
    fun getShippingAddress(@Header("Authorization") bearer:String): Call<ShippingAddress>

    @POST("/index.php?route=rest/shipping_address/shippingaddress&existing=1")
    fun getShippingAddressPost(@Header("Authorization") bearer:String, @Body map: Map<String, String>): Call<ShippingAddressPost>

    @GET("/index.php?route=rest/shipping_method/shippingmethods")
    fun getShippingMethodGet(@Header("Authorization") bearer:String): Call<ShippingMethodGet>

    @GET("/index.php?route=rest/payment_method/payments")
    fun getPaymentMethodGet(@Header("Authorization") bearer:String): Call<PaymentMethodGet>

    @POST("/index.php?route=rest/shipping_method/shippingmethods")
    fun getShippingMethodPost(@Header("Authorization") bearer:String, @Body map: Map<String, String>): Call<ShippingMethodPost>

    @POST("/index.php?route=rest/payment_method/payments")
    fun getPaymentMethodPost(@Header("Authorization") bearer:String, @Body map: Map<String, String>): Call<PaymentsMethodPost>

    @POST("/index.php?route=rest/confirm/confirm")
    fun getConfirmPost(@Header("Authorization") bearer:String): Call<ConfirmPost>

    @PUT("/index.php?route=rest/confirm/confirm")
    fun getConfirmPut(@Header("Authorization") bearer:String): Call<ConfirmPut>

    @PUT("/index.php?route=rest/account/newsletter&subscribe=1")
    fun getSubscribe(@Header("Authorization") bearer:String): Call<Subscribe>

    @DELETE("/index.php?route=rest/wishlist/wishlist")
    fun favoriteDelete(@Header("Authorization") bearer:String, @Query("id") id:Int): Call<DeleteFavorite>
}