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
import kotlinx.android.synthetic.main.fragment_profile_setting_rename_contact_name.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class ProfileSettingRenameContactNameFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile_setting_rename_contact_name, container, false)
        val btnSave = view.findViewById<Button>(R.id.button)

        val editTextTextPersonFName = view.findViewById<EditText>(R.id.editTextTextPersonFName)
        val editTextTextPersonSName = view.findViewById<EditText>(R.id.editTextTextPersonSName)
        val editTextTextPersonEmail = view.findViewById<EditText>(R.id.editTextTextPersonEmail)
        val editTextTextPersonPhone = view.findViewById<EditText>(R.id.editTextTextPersonPhone)
        val editTextTextPersonFax = view.findViewById<EditText>(R.id.editTextTextPersonFax)

        getData()

        btnSave.setOnClickListener {
            saveData(
                editTextTextPersonFName.text.toString(),
                editTextTextPersonSName.text.toString(),
                editTextTextPersonEmail.text.toString(),
                editTextTextPersonPhone.text.toString(),
                editTextTextPersonFax.text.toString()
            )
        }

        return view
    }

    private fun saveData(fname: String, sname: String, email: String, phone: String, fax: String) {
        val map = HashMap<String, String>()
        map["firstname"] = fname
        map["lastname"] = sname
        map["email"] = email
        map["telephone"] = phone
        map["fax"] = fax

        val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getAuthNewData("Bearer " + bearer.getString("access_token", "empty").toString(), map).awaitResponse()
                if (response.isSuccessful) {
                    getData()
                    withContext(Dispatchers.Main) {
                        Toast.makeText((activity as FragmentActivity), "Данные успешно изменены", Toast.LENGTH_SHORT).show()
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

    private fun getData() {
        val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
        val getName: SharedPreferences =  (activity as FragmentActivity).getSharedPreferences("GET_NAME", Context.MODE_PRIVATE)

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getAuthUser("Bearer " + bearer.getString("access_token", "empty").toString()).awaitResponse()
                if (response.isSuccessful) {
                    Thread {
                        (activity as FragmentActivity).runOnUiThread(java.lang.Runnable {
                            if (editTextTextPersonFName != null && editTextTextPersonSName != null && editTextTextPersonEmail != null && editTextTextPersonPhone != null && editTextTextPersonFax != null) {
                                val editor = getName.edit()
                                editor.putString("getName", response.body()?.data?.firstname + " " + response.body()?.data?.lastname).apply()
                                val data = response.body()?.data
                                editTextTextPersonFName.setText(data?.firstname)
                                editTextTextPersonSName.setText(data?.lastname)
                                editTextTextPersonEmail.setText(data?.email)
                                editTextTextPersonPhone.setText(data?.telephone)
                                editTextTextPersonFax.setText(data?.fax)
                            }
                        })
                    }.start()
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