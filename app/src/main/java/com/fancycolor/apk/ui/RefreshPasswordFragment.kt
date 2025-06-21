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
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.fancycolor.apk.ApiRequests
import com.fancycolor.apk.BASE_URL
import com.fancycolor.apk.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class RefreshPasswordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_refresh_password, container, false)
        val email = view.findViewById<EditText>(R.id.editTextEmail)
        val btnRefresh = view.findViewById<Button>(R.id.refreshButton)

        btnRefresh.setOnClickListener {
            val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)

            val map = HashMap<String, String>()
            map["email"] = email.text.toString()

            val api = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRequests::class.java)

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val response = api.refreshPassword("Bearer " + bearer.getString("access_token", "empty").toString(), map).awaitResponse()
                    if (response.isSuccessful) {
                        (activity as FragmentActivity).supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, AuthLoginFragment())
                            .addToBackStack(null)
                            .commit()
                    }
                    else {
                        when (response.code()) {
                            400 -> {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(activity as FragmentActivity, "Неверный email", Toast.LENGTH_SHORT).show()
                                }
                            }
                            else -> Log.d("AuthStatus", response.code().toString())
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(activity as FragmentActivity, "Неправильный Email адрес", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return view
    }

}