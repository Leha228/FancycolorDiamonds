package com.fancycolor.apk

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class PayActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)

        val webView = findViewById<WebView>(R.id.paymasterView)
        val webForm: SharedPreferences = getSharedPreferences("WEB_FORM", Context.MODE_PRIVATE)

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                if (url.contains("https://fancycolor.world/index.php?route=checkout/success")) {
                    val bearer: SharedPreferences = getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
                    val api = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(ApiRequests::class.java)

                    GlobalScope.launch(Dispatchers.IO) {
                        try {
                            val response = api.getConfirmPut("Bearer " + bearer.getString("access_token", "empty").toString()).awaitResponse()
                            if (response.isSuccessful) {
                                Log.d("getConfirmPut", "Success")
                                Log.d("ConfirmPUT", response.body()?.data.toString())
                                finish()
                            }
                            else {
                                Log.d("getConfirmPut", response.code().toString())
                            }
                        } catch (e: Exception) {
                            Log.d("getConfirmPut", "Skip")
                        }
                    }
                }
                else  {
                    finish()
                }
            }
        }

        webView.settings.javaScriptEnabled = true

        val details = webForm.getString("webForm", "Empty")
        val mimeType: String = "text/html"
        val utfType: String = "UTF-8"

        if (details != null) {
            webView.loadData("$details<script>document.querySelector('.btn').click()</script>", mimeType, utfType)
            Log.d("PayActivity", "Running")
            webView.visibility = View.VISIBLE
        }
    }

}