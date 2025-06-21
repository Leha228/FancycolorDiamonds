package com.example.fancycolordiamonds.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fancycolordiamonds.*
import com.fancycolor.apk.R
import kotlinx.android.synthetic.main.fragment_country_register.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class CountryRegisterFragment : DialogFragment() {

    private val countryAdapter by lazy { CountryAdapter(onClick = { name, id -> onClick(name, id) }) }
    lateinit var listener: CountryRegisterFragment.CountryListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_country_register, container, false)
        getCountry()
        return view
    }

    private fun getCountry() {
        val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getCountry("Bearer " + bearer.getString("access_token", "empty").toString()).awaitResponse()
                if (response.isSuccessful) {
                    if ((activity as FragmentActivity != null)) {
                        Thread {
                            (activity as FragmentActivity).runOnUiThread(java.lang.Runnable {
                                if (recyclerViewCountryRegister != null) {
                                    recyclerViewCountryRegister?.adapter = countryAdapter
                                    recyclerViewCountryRegister?.layoutManager = GridLayoutManager(activity as FragmentActivity, 1)
                                    response.body()?.data?.let { countryAdapter.setData(it) }
                                }
                            })
                        }.start()
                        Log.d("Retrofit", "Success")
                    }
                }
                else {
                    Log.d("Retrofit", response.code().toString())
                }
            } catch (e: Exception) {
                Log.d("Retrofit", "Skip")
            }
        }
    }

    private fun onClick(name : String, id : Int) {
        //USE IF NEED :)
//        val countryName: SharedPreferences = requireActivity().getSharedPreferences("COUNTRY_NAME", Context.MODE_PRIVATE)
//        val countryID: SharedPreferences = requireActivity().getSharedPreferences("COUNTRY_ID", Context.MODE_PRIVATE)
//        val editorCountryName = countryName.edit()
//        val editorCountryID = countryID.edit()
//        editorCountryName.putString("CountryName", name).apply()
//        editorCountryID.putString("CountryID", id.toString()).apply()
        listener.pickCountry(name)
        this.dismiss()
    }

    interface CountryListener {
        fun pickCountry(name : String)
    }
}