package com.fancycolor.apk.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity
import com.fancycolor.apk.ApiRequests
import com.fancycolor.apk.BASE_URL
import com.fancycolor.apk.R
import kotlinx.android.synthetic.main.fragment_auth_register.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory


class AuthRegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_auth_register, container, false)

        val countryName: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("COUNTRY_NAME", Context.MODE_PRIVATE)
        val countryID: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("COUNTRY_ID", Context.MODE_PRIVATE)
        val regionName: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("REGION_NAME", Context.MODE_PRIVATE)
        val regionID: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("REGION_ID", Context.MODE_PRIVATE)
        val subscribe: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("SUBSCRIBE", Context.MODE_PRIVATE)

        val countryText = view.findViewById<EditText>(R.id.regCountryText)
        val regionText = view.findViewById<EditText>(R.id.regRegionText)
        val regBtn = view.findViewById<Button>(R.id.registerButton)
        val radioGroup = view.findViewById<RadioGroup>(R.id.radioEmailGroup)

        countryText.setRawInputType(0x00000000)
        regionText.setRawInputType(0x00000000)

        if (countryName.contains("CountryName")) {
            countryText.setText(countryName.getString("CountryName", "Empty"))
        }

        if (regionName.contains("RegionName")) {
            regionText.setText(regionName.getString("RegionName", "Empty"))
        }

        countryText.setOnClickListener{
            val dialog = CountryRegisterFragment()
            dialog.listener = object : CountryRegisterFragment.CountryListener {
                override fun pickCountry(name: String) {
                    countryText.setText(name)
                }
            }
            dialog.show((activity as FragmentActivity).supportFragmentManager, "CountryRegisterFragment")
          //  CountryRegisterFragment().show((activity as FragmentActivity).supportFragmentManager, null)
        }

        regionText.setOnClickListener{
            val dialog = RegionRegisterFragment()
            dialog.listener = object : RegionRegisterFragment.RegionListener {
                override fun pickRegion(name : String) {
                    regionText.setText(name)
                }
            }
            dialog.show((activity as FragmentActivity).supportFragmentManager, "RegionRegisterFragment")
        //    RegionRegisterFragment().show((activity as FragmentActivity).supportFragmentManager, null)
        }

        regBtn.setOnClickListener {
            if (regionName.contains("RegionName") && countryName.contains("CountryName")) {
                registerAccount()
            }
        }

        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            val radio = view.findViewById<RadioButton>(checkedId)
            if (radio == view.findViewById(R.id.radioEmailYes)) {
                val editorEmailSubscribe = subscribe.edit()
                editorEmailSubscribe.putBoolean("subscribe", true).apply()
            }
        })

        return view
    }

    private fun registerAccount() {
        val map = HashMap<String, String>()
        map["firstname"] = regNameText.text.toString()
        map["lastname"] = regSnameText.text.toString()
        map["email"] = regEmailText.text.toString()
        map["telephone"] = regPhoneText.text.toString()
        map["country_id"] = regCountryText.text.toString()
        map["zone_id"] = regRegionText.text.toString()
        map["city"] = regCityText.text.toString()
        map["address_1"] = regAddressText.text.toString()
        map["password"] = regPasswordText.text.toString()
        map["confirm"] = regConfirmPasswordText.text.toString()
        map["agree"] = 1.toString()
        map["customer_group_id"] = 1.toString()
        map["tax_id"] = 1.toString()
        map["postcode"] = "0000"

        val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.registerAccount("Bearer " + bearer.getString("access_token", "empty").toString(), map).awaitResponse()
                if (response.isSuccessful) {
                    Log.d("Register", "Success")
                    (activity as FragmentActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, SuccessRegisterFragment())
                        .addToBackStack(null)
                        .commit()
                }
                else {
                    Log.d("Register", response.code().toString())
                    Log.d("Error", response.body().toString())
                }
            } catch (e: Exception) {
                Log.d("Register", "Skip")
            }
        }

    }

}