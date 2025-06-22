package com.fancycolor.apk.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.FragmentActivity
import com.fancycolor.apk.R


class PaymasterFragment : Fragment() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_paymaster, container, false)

        val webView = view.findViewById<WebView>(R.id.paymasterView)
        webView.visibility = View.GONE
        val webForm: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("WEB_FORM", Context.MODE_PRIVATE)

        val details = webForm.getString("webForm", "Empty")
        val mimeType: String = "text/html"
        val utfType: String = "UTF-8"

        if (details != null) {
            Log.d("paymasters", details)
            webView.webViewClient = WebViewClient()
            webView.settings.javaScriptEnabled = true
            webView.loadData("$details<script>document.querySelector('.btn').click()</script>", mimeType, utfType)
            Log.d("PayMasterFragment", "Running")
            webView.visibility = View.VISIBLE
        }

        return view
    }

}