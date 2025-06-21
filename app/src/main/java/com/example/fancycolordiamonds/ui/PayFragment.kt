package com.example.fancycolordiamonds.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fancycolordiamonds.*
import com.fancycolor.apk.R
import kotlinx.android.synthetic.main.fragment_pay.*
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class PayFragment : Fragment() {

    private val cartAdapter by lazy { CartPayAdapter() }
    private var payBtn: Button? = null
    private var addressId: Int? = null
    private var addressIdShipping: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pay, container, false)
        val payCountry = view.findViewById<EditText>(R.id.payCountryText)
        val payRegion = view.findViewById<EditText>(R.id.payRegionText)
        val radioGroup = view.findViewById<RadioGroup>(R.id.radioAddressGroup)
        val radioGroupPayments = view.findViewById<RadioGroup>(R.id.radioPaymentsGroup)
        val radioGroupPay = view.findViewById<RadioGroup>(R.id.radioPayGroup)
        payBtn = view.findViewById<Button>(R.id.payBtn)

        getPaymentAddress() // Получаем платежный адрес пользователя
        getShippingAddress() // Получаем адрес доставки пользователя

        // New address

        val nameLabel = view.findViewById<TextView>(R.id.payNameLabel)
        val nameText = view.findViewById<EditText>(R.id.payNameText)
        val companyLabel = view.findViewById<TextView>(R.id.payCompanyLabel)
        val companyText = view.findViewById<EditText>(R.id.payCompanyText)
        val addressLabel = view.findViewById<TextView>(R.id.payAddressLabel)
        val addressText = view.findViewById<EditText>(R.id.payAddressText)
        val cityLabel = view.findViewById<TextView>(R.id.payCityLabel)
        val cityText = view.findViewById<EditText>(R.id.payCityText)
        val indexLabel = view.findViewById<TextView>(R.id.payIndexLabel)
        val indexText = view.findViewById<EditText>(R.id.payIndexText)
        val countryLabel = view.findViewById<TextView>(R.id.payCountryLabel)
        val countryText = view.findViewById<EditText>(R.id.payCountryText)
        val regionLabel = view.findViewById<TextView>(R.id.payRegionLabel)
        val regionText = view.findViewById<EditText>(R.id.payRegionText)

        val namePaymentLabel = view.findViewById<TextView>(R.id.payNameLabelPayment)
        val namePaymentText = view.findViewById<EditText>(R.id.payNameTextPayment)
        val snamePaymentLabel = view.findViewById<TextView>(R.id.paySNameLabelPayment)
        val snamePaymentText = view.findViewById<EditText>(R.id.paySNameTextPayment)
        val addressPaymentLabel = view.findViewById<TextView>(R.id.payAddressLabelPayment)
        val addressPaymentText = view.findViewById<EditText>(R.id.payAddressTextPayment)
        val cityPaymentLabel = view.findViewById<TextView>(R.id.payCityLabelPayment)
        val cityPaymentText = view.findViewById<EditText>(R.id.payCityTextPayment)
        val indexPaymentLabel = view.findViewById<TextView>(R.id.payIndexLabelPayment)
        val indexPaymentText = view.findViewById<EditText>(R.id.payIndexTextPayment)
        val countryPaymentLabel = view.findViewById<TextView>(R.id.payCountryLabelPayment)
        val countryPaymentText = view.findViewById<EditText>(R.id.payCountryTextPayment)
        val regionPaymentLabel = view.findViewById<TextView>(R.id.payRegionLabelPayment)
        val regionPaymentText = view.findViewById<EditText>(R.id.payRegionTextPayment)

        val countryName: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("COUNTRY_NAME", Context.MODE_PRIVATE)
        val countryID: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("COUNTRY_ID", Context.MODE_PRIVATE)
        val regionName: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("REGION_NAME", Context.MODE_PRIVATE)
        val regionID: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("REGION_ID", Context.MODE_PRIVATE)

        countryText.setRawInputType(0x00000000)
        regionText.setRawInputType(0x00000000)

        countryPaymentText.setRawInputType(0x00000000)
        regionPaymentText.setRawInputType(0x00000000)

        if (countryName.contains("CountryName")) {
            countryPaymentText.setText(countryName.getString("CountryName", "Empty"))
            countryText.setText(countryName.getString("CountryName", "Empty"))
        }

        if (regionName.contains("RegionName")) {
            regionPaymentText.setText(regionName.getString("RegionName", "Empty"))
            regionText.setText(regionName.getString("RegionName", "Empty"))
        }

        countryText.setOnClickListener{
            CountryRegisterFragment().show((activity as FragmentActivity).supportFragmentManager, null)
        }

        regionText.setOnClickListener{
            RegionRegisterFragment().show((activity as FragmentActivity).supportFragmentManager, null)
        }

        countryPaymentText.setOnClickListener{
            CountryRegisterFragment().show((activity as FragmentActivity).supportFragmentManager, null)
        }

        regionPaymentText.setOnClickListener{
            RegionRegisterFragment().show((activity as FragmentActivity).supportFragmentManager, null)
        }

        nameText.visibility = View.GONE
        nameLabel.visibility = View.GONE
        companyLabel.visibility = View.GONE
        companyText.visibility = View.GONE
        addressLabel.visibility = View.GONE
        addressText.visibility = View.GONE
        cityLabel.visibility = View.GONE
        cityText.visibility = View.GONE
        indexLabel.visibility = View.GONE
        indexText.visibility = View.GONE
        countryLabel.visibility = View.GONE
        countryText.visibility = View.GONE
        regionLabel.visibility = View.GONE
        regionText.visibility = View.GONE

        namePaymentText.visibility = View.GONE
        namePaymentLabel.visibility = View.GONE
        snamePaymentLabel.visibility = View.GONE
        snamePaymentText.visibility = View.GONE
        addressPaymentLabel.visibility = View.GONE
        addressPaymentText.visibility = View.GONE
        cityPaymentLabel.visibility = View.GONE
        cityPaymentText.visibility = View.GONE
        indexPaymentLabel.visibility = View.GONE
        indexPaymentText.visibility = View.GONE
        countryPaymentLabel.visibility = View.GONE
        countryPaymentText.visibility = View.GONE
        regionPaymentLabel.visibility = View.GONE
        regionPaymentText.visibility = View.GONE

        // End new address

        payCountry.setRawInputType(0x00000000)
        payRegion.setRawInputType(0x00000000)

        payCountry.setOnClickListener {
            CountryRegisterFragment().show((activity as FragmentActivity).supportFragmentManager, null)
        }

       radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
           val radio = view.findViewById<RadioButton>(checkedId)
           if (radio == view.findViewById(R.id.radioButtonDefaultAddress)) {
               nameText.visibility = View.GONE
               nameLabel.visibility = View.GONE
               companyLabel.visibility = View.GONE
               companyText.visibility = View.GONE
               addressLabel.visibility = View.GONE
               addressText.visibility = View.GONE
               cityLabel.visibility = View.GONE
               cityText.visibility = View.GONE
               indexLabel.visibility = View.GONE
               indexText.visibility = View.GONE
               countryLabel.visibility = View.GONE
               countryText.visibility = View.GONE
               regionLabel.visibility = View.GONE
               regionText.visibility = View.GONE
           }
           else {
               nameText.visibility = View.VISIBLE
               nameLabel.visibility = View.VISIBLE
               companyLabel.visibility = View.VISIBLE
               companyText.visibility = View.VISIBLE
               addressLabel.visibility = View.VISIBLE
               addressText.visibility = View.VISIBLE
               cityLabel.visibility = View.VISIBLE
               cityText.visibility = View.VISIBLE
               indexLabel.visibility = View.VISIBLE
               indexText.visibility = View.VISIBLE
               countryLabel.visibility = View.VISIBLE
               countryText.visibility = View.VISIBLE
               regionLabel.visibility = View.VISIBLE
               regionText.visibility = View.VISIBLE
           }
       })

        radioGroupPayments.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            val radio = view.findViewById<RadioButton>(checkedId)
            if (radio == view.findViewById(R.id.radioButtonDefaultPayment)) {
                namePaymentText.visibility = View.GONE
                namePaymentLabel.visibility = View.GONE
                snamePaymentLabel.visibility = View.GONE
                snamePaymentText.visibility = View.GONE
                addressPaymentLabel.visibility = View.GONE
                addressPaymentText.visibility = View.GONE
                cityPaymentLabel.visibility = View.GONE
                cityPaymentText.visibility = View.GONE
                indexPaymentLabel.visibility = View.GONE
                indexPaymentText.visibility = View.GONE
                countryPaymentLabel.visibility = View.GONE
                countryPaymentText.visibility = View.GONE
                regionPaymentLabel.visibility = View.GONE
                regionPaymentText.visibility = View.GONE
            }
            else {
                namePaymentText.visibility = View.VISIBLE
                namePaymentLabel.visibility = View.VISIBLE
                snamePaymentLabel.visibility = View.VISIBLE
                snamePaymentText.visibility = View.VISIBLE
                addressPaymentLabel.visibility = View.VISIBLE
                addressPaymentText.visibility = View.VISIBLE
                cityPaymentLabel.visibility = View.VISIBLE
                cityPaymentText.visibility = View.VISIBLE
                indexPaymentLabel.visibility = View.VISIBLE
                indexPaymentText.visibility = View.VISIBLE
                countryPaymentLabel.visibility = View.VISIBLE
                countryPaymentText.visibility = View.VISIBLE
                regionPaymentLabel.visibility = View.VISIBLE
                regionPaymentText.visibility = View.VISIBLE
            }
        })

        radioGroupPay.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            val radio = view.findViewById<RadioButton>(checkedId)
            if (radio == view.findViewById(R.id.radioPayMaster)) {
                payBtn?.text = "перейти к оплате"
            }
            else {
                payBtn?.text = "заказать"
            }
        })

        payBtn?.setOnClickListener {
            getShippingAddressPost()
        }

        getBasket()
        return view
    }

    private fun getShippingMethodGet() {
        val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getShippingMethodGet("Bearer " + bearer.getString("access_token", "empty").toString()).awaitResponse()
                if (response.isSuccessful) {
                    Log.d("getShippingMethodGet", "Success")
                    getPaymentAddressPost()
                    //getShippingMethodPost()
                }
                else {
                    Log.d("getShippingMethodGet", response.code().toString())
                }
            } catch (e: Exception) {
                Log.d("getShippingMethodGet", "Skip")
            }
        }
    }

    private fun getShippingMethodPost() {
        val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        val map = HashMap<String, String>()
        map["shipping_method"] = "flat.flat"
        map["comment"] = "string"

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getShippingMethodPost("Bearer " + bearer.getString("access_token", "empty").toString(), map).awaitResponse()
                if (response.isSuccessful) {
                    Log.d("getShippingMethodPost", "Success")
                    getPaymentMethodGet()
                }
                else {
                    Log.d("getShippingMethodPost", response.code().toString())
                    Log.d("Error", response.body().toString())
                }
            } catch (e: Exception) {
                Log.d("getShippingMethodPost", "Skip")
            }
        }
    }

    private fun getPaymentMethodGet() {
        val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getPaymentMethodGet("Bearer " + bearer.getString("access_token", "empty").toString()).awaitResponse()
                if (response.isSuccessful) {
                    Log.d("getPaymentMethodGet", "Success")
                    getPaymentMethodPost()
                }
                else {
                    Log.d("getPaymentMethodGet", response.code().toString())
                }
            } catch (e: Exception) {
                Log.d("getPaymentMethodGet", "Skip")
            }
        }
    }

    private fun getPaymentMethodPost() {
        val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        val map = HashMap<String, String>()

        if (payBtn?.text.toString() == "заказать") {
            map["payment_method"] = "cod"
        }
        else {
            map["payment_method"] = "paymaster"
        }
        map["agree"] = 1.toString()
        map["comment"] = editTextTextMultiLine.text.toString()

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getPaymentMethodPost("Bearer " + bearer.getString("access_token", "empty").toString(), map).awaitResponse()
                if (response.isSuccessful) {
                    Log.d("getPaymentMethodPost", "Success")
                    getConfirmPost()
                }
                else {
                    Log.d("getPaymentMethodPost", response.code().toString())
                }
            } catch (e: Exception) {
                Log.d("getPaymentMethodPost", "Skip")
            }
        }
    }

    private fun getConfirmPost() {
        val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getConfirmPost("Bearer " + bearer.getString("access_token", "empty").toString()).awaitResponse()
                if (response.isSuccessful) {
                    Log.d("getConfirmPost", "Success")
                    Log.d("ConfirmPOST", response.body()?.data.toString())
                    if (payBtn?.text == "перейти к оплате") {
                        //response.body()?.data?.payment?.let { Log.d("Payment", it) }

                        val webForm: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("WEB_FORM", Context.MODE_PRIVATE)
                        val editor = webForm.edit()
                        editor.putString("webForm", response.body()?.data?.payment).apply()

                        GlobalScope.launch(Dispatchers.Main) {
                            getToPay()
                        }

                    }
                    else {
                        getConfirmPut()
                    }
                }
                else {
                    Log.d("getConfirmPost", response.code().toString())
                }
            } catch (e: Exception) {
                Log.d("getConfirmPost", "Skip")
            }
        }
    }

    private fun getToPay() {
        val intent = Intent(activity, PayActivity::class.java)
        startActivity(intent)
    }

    private fun getConfirmPut() {
        val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
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
                    (activity as FragmentActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, BacketFragment())
                        .addToBackStack(null)
                        .commit()
                }
                else {
                    Log.d("getConfirmPut", response.code().toString())
                }
            } catch (e: Exception) {
                Log.d("getConfirmPut", "Skip")
            }
        }
    }

    private fun getPaymentAddress() {
        val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getPaymentAddress("Bearer " + bearer.getString("access_token", "empty").toString()).awaitResponse()
                if (response.isSuccessful) {
                    Thread {
                        (activity as FragmentActivity).runOnUiThread(java.lang.Runnable {
                            val data  = response.body()?.data?.addresses?.get(0)
                            addressId = data?.addressId?.toInt()
                            radioButtonDefaultPayment.text = data?.firstname + " " + data?.lastname + " " + data?.city + " " + data?.zone + " " + data?.country
                        })
                    }.start()
                    Log.d("getPaymentAddress", "Success")
                }
                else {
                    Log.d("getPaymentAddress", response.code().toString())
                }
            } catch (e: Exception) {
                Log.d("getPaymentAddress", "Skip")
            }
        }
    }

    private fun getPaymentAddressPost() {
        val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        val map = HashMap<String, String>()
        map["address_id"] = addressId.toString()
        Log.d("PaymentAddressID", addressId.toString())

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getPaymentAddressPost("Bearer " + bearer.getString("access_token", "empty").toString(), map).awaitResponse()
                if (response.isSuccessful) {
                    Log.d("getPaymentAddressPost", "Success")
                    getShippingMethodPost()
                }
                else {
                    Log.d("getPaymentAddressPost", response.code().toString())
                }
            } catch (e: Exception) {
                Log.d("getPaymentAddressPost", "Skip")
            }
        }
    }

    private fun getShippingAddress() {
        val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getShippingAddress("Bearer " + bearer.getString("access_token", "empty").toString()).awaitResponse()
                if (response.isSuccessful) {
                    Thread {
                        (activity as FragmentActivity).runOnUiThread(java.lang.Runnable {
                            val data  = response.body()?.data?.addresses?.get(0)
                            addressIdShipping = data?.addressId?.toInt()
                            radioButtonDefaultAddress.text = data?.firstname + " " + data?.lastname + " " + data?.city + " " + data?.zone + " " + data?.country
                        })
                    }.start()
                    Log.d("getShippingAddress", "Success")
                }
                else {
                    Log.d("getShippingAddress", response.code().toString())
                }
            } catch (e: Exception) {
                Log.d("getShippingAddress", "Skip")
            }
        }
    }

    private fun getShippingAddressPost() {
        val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        val map = HashMap<String, String>()
        map["address_id"] = addressIdShipping.toString()
        Log.d("ShippingAddressID", addressIdShipping.toString())

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getShippingAddressPost("Bearer " + bearer.getString("access_token", "empty").toString(), map).awaitResponse()
                if (response.isSuccessful) {
                    Log.d("getShippingAddressPost", "Success")
                    getShippingMethodGet()
                }
                else {
                    Log.d("getShippingAddressPost", response.code().toString())
                }
            } catch (e: Exception) {
                Log.d("getShippingAddressPost", "Skip")
            }
        }
    }

    private fun getBasket() {
        val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getProductBacket("Bearer " + bearer.getString("access_token", "empty").toString()).awaitResponse()
                if (response.isSuccessful) {
                    Thread {
                        (activity as FragmentActivity).runOnUiThread(java.lang.Runnable {
                            if (recyclerviewpayproduct != null) {
                                recyclerviewpayproduct.adapter = cartAdapter
                                recyclerviewpayproduct.layoutManager =
                                    GridLayoutManager(activity as FragmentActivity, 1)
                                response.body()?.data?.products?.let { cartAdapter.setData(it) }
                                totalPriceValue.text = response.body()?.data?.total
                            }
                        })
                    }.start()
                    Log.d("Retrofit", "Success")
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