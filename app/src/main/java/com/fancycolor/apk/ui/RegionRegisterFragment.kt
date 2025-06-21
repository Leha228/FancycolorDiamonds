package com.fancycolor.apk.ui

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
import com.fancycolor.apk.ApiRequests
import com.fancycolor.apk.BASE_URL
import com.fancycolor.apk.R
import com.fancycolor.apk.RegionAdapter
import kotlinx.android.synthetic.main.fragment_region_register.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory


class RegionRegisterFragment : DialogFragment() {

    private val regionAdapter by lazy { RegionAdapter(onClick = { name, id -> onClick(name, id) }) }
    lateinit var listener: RegionListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_region_register, container, false)
        val countryID: SharedPreferences =
            (activity as FragmentActivity).getSharedPreferences("COUNTRY_ID", Context.MODE_PRIVATE)
        if (countryID.contains("CountryID")) {
            countryID.getString("CountryID", "Empty")?.let { getRegion(it) }
        } else {
            this.dismiss()
        }
        return view
    }

    private fun getRegion(id: String) {
        val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences(
            "ACCESS_TOKEN",
            Context.MODE_PRIVATE
        )
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getRegion(
                    "Bearer " + bearer.getString("access_token", "empty").toString(),
                    id.toInt()
                ).awaitResponse()
                if (response.isSuccessful) {
                    if ((activity as FragmentActivity != null)) {
                        Thread {
                            (activity as FragmentActivity).runOnUiThread(java.lang.Runnable {
                                if (recyclerViewRegionRegister != null) {
                                    val zone = response.body()?.data?.zone
                                    recyclerViewRegionRegister?.adapter = regionAdapter
                                    recyclerViewRegionRegister?.layoutManager =
                                        GridLayoutManager(activity as FragmentActivity, 1)
                                    if (zone != null) {
                                        regionAdapter.setData(zone)
                                    }
                                }
                            })
                        }.start()
                        Log.d("Retrofit", "Success")
                    }
                } else {
                    Log.d("Retrofit", response.code().toString())
                }
            } catch (e: Exception) {
                Log.d("Retrofit", "Skip")
            }
        }
    }


    private fun onClick(name : String, id : String) {
        //USE IF NEED :)
//        val regionName: SharedPreferences = requireActivity().getSharedPreferences("REGION_NAME", Context.MODE_PRIVATE)
//        val regionID: SharedPreferences = requireActivity().getSharedPreferences("REGION_ID", Context.MODE_PRIVATE)
//        val editorRegionName = regionName.edit()
//        val editorRegionID = regionID.edit()
//        editorRegionName.putString("RegionName", name).apply()
//        editorRegionID.putString("RegionID", id).apply()
        listener.pickRegion(name)
        this.dismiss()
    }

    interface RegionListener {
        fun pickRegion(name : String)
    }
}