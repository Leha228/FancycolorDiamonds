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

class ProfileSettingPasswordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile_setting_password, container, false)
        val btnSave = view.findViewById<Button>(R.id.button)
        val editTextTextPassword = view.findViewById<EditText>(R.id.editTextTextPassword)
        val editTextTextPasswordNew = view.findViewById<EditText>(R.id.editTextTextPasswordNew)

        btnSave.setOnClickListener {
            if (editTextTextPassword.text.toString() == editTextTextPasswordNew.text.toString()) {
                saveData(
                    editTextTextPassword.text.toString(),
                    editTextTextPasswordNew.text.toString()
                )
            }
            else {
                Toast.makeText((activity as FragmentActivity), "Пароль не совпадают", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun saveData(pass: String, conf: String) {
        val map = HashMap<String, String>()
        map["password"] = pass
        map["confirm"] = conf

        val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getAuthNewPassword("Bearer " + bearer.getString("access_token", "empty").toString(), map).awaitResponse()
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText((activity as FragmentActivity), "Пароль успешно изменен", Toast.LENGTH_SHORT).show()
                    }
                    Log.d("AuthStatusNewData", "Success")
                }
                else {
                    Log.d("AuthStatus", response.code().toString())
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText((activity as FragmentActivity), "No link internet", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

}