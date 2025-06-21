package com.fancycolor.apk.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.fancycolor.apk.ApiRequests
import com.fancycolor.apk.BASE_URL
import com.fancycolor.apk.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val subscribe: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("SUBSCRIBE", Context.MODE_PRIVATE)
        if (subscribe.contains("subscribe")) {
            getSubscribe()
            val editorEmailSubscribe = subscribe.edit()
            editorEmailSubscribe.putBoolean("subscribe", false).apply()
        }

        val img1 = view.findViewById<ImageView>(R.id.imageView3)
        val img2 = view.findViewById<ImageView>(R.id.imageView4)
        val img3 = view.findViewById<ImageView>(R.id.imageView5)
        val img4 = view.findViewById<ImageView>(R.id.imageView6)
        Picasso.get().load("https://fancycolor.world/ring.jpg").into(img1)
        Picasso.get().load("https://fancycolor.world/hb.jpg").into(img2)
        Picasso.get().load("https://fancycolor.world/gold.jpg").into(img3)
        Picasso.get().load("https://fancycolor.world/bank.jpg").into(img4)

        img1.setOnClickListener {
            replaceFragment(ShareOneFragment())
        }
        img2.setOnClickListener {
            replaceFragment(ShareTwoFragment())
        }
        img3.setOnClickListener {
            replaceFragment(ShareThreeFragment())
        }
        img4.setOnClickListener {
            replaceFragment(ShareFourFragment())
        }

        return view
    }

    private fun replaceFragment(fragment: Fragment) {
        (activity as FragmentActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun getSubscribe() {
        val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getSubscribe("Bearer " + bearer.getString("access_token", "empty").toString()).awaitResponse()
                if (response.isSuccessful) {
                    Log.d("Subscribe", "True")
                }
                else {
                    Log.d("Retrofit", response.code().toString())
                }
            } catch (e: Exception) {
                Log.d("Retrofit", "Skip")
            }
        }
    }
}