package com.fancycolor.apk.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.fancycolor.apk.*
import kotlinx.android.synthetic.main.fragment_backet.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class BacketFragment : Fragment() {

    private val cartAdapter by lazy { CartAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_backet, container, false)

        val login = view.findViewById<Button>(R.id.login)
        val register = view.findViewById<Button>(R.id.register)
        val pay = view.findViewById<Button>(R.id.buttonPay)
        val btnCatalog = view.findViewById<Button>(R.id.buttonCatalog)

        val info = view.findViewById<ConstraintLayout>(R.id.constraintLayoutInfoBack)
        info.visibility = View.GONE
        val auth: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_AUTH", Context.MODE_PRIVATE)

        if (auth.contains("access_auth")) {
            if (auth.getBoolean("access_auth", false)) {
                login.visibility = View.GONE
                register.visibility = View.GONE
                getProductBacketAll()
            }
            else {
                login.visibility = View.VISIBLE
                register.visibility = View.VISIBLE
            }
        }
        else {
            Log.d("BacketFragment", "No access_auth shared")
        }

        login.setOnClickListener {
            (activity as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AuthLoginFragment())
                .addToBackStack(null)
                .commit()
        }

        register.setOnClickListener {
            (activity as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AuthRegisterFragment())
                .addToBackStack(null)
                .commit()
        }

        pay.setOnClickListener {
            (activity as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PayFragment())
                .addToBackStack(null)
                .commit()
        }

        btnCatalog.setOnClickListener {
            (activity as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CatalogFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }

    private fun getProductBacketAll() {
        val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getProductBacket("Bearer " + bearer.getString("access_token", "empty").toString()).awaitResponse()
                val data = response.body()?.data
                if (response.isSuccessful) {
                    Thread {
                        (activity as FragmentActivity).runOnUiThread(java.lang.Runnable {
                            textEmpty?.visibility = View.GONE
                            buttonCatalog?.visibility = View.GONE
                            recyclerViewBacket?.visibility = View.VISIBLE
                            constraintLayoutInfoBack?.visibility = View.VISIBLE
                            constraintLayoutBuy?.visibility = View.VISIBLE
                            if (recyclerViewBacket != null) {
                                recyclerViewBacket.adapter = cartAdapter
                                backetInfoText.text = response.body()?.data?.total
                                recyclerViewBacket.layoutManager = GridLayoutManager(activity as FragmentActivity, 1)
                                response.body()?.data?.products?.let { cartAdapter.setData(it) }
                            }
                        })
                    }.start()
                    Log.d("Retrofit", "Success")
                } else {
                    Log.d("Retrofit", response.code().toString())
                }
            }
            catch (e: Exception) {
                Log.d("Retrofit", "Skip")
                Thread {
                    (activity as FragmentActivity).runOnUiThread(java.lang.Runnable {
                        if (recyclerViewBacket != null) {
                            recyclerViewBacket.visibility = View.GONE
                            constraintLayoutInfoBack.visibility = View.GONE
                            constraintLayoutBuy.visibility = View.GONE
                            textEmpty.visibility = View.VISIBLE
                            buttonCatalog.visibility = View.VISIBLE
                        }
                    })
                }.start()
            }
        }
    }

}