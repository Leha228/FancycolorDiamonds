package com.fancycolor.apk.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.fancycolor.apk.ApiRequests
import com.fancycolor.apk.BASE_URL
import com.fancycolor.apk.MainActivity
import com.fancycolor.apk.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class AuthLoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_auth_login, container, false)
        val loginButton = view.findViewById<Button>(R.id.loginButton)
        val emailText = view.findViewById<EditText>(R.id.editTextTextEmail)
        val passText = view.findViewById<EditText>(R.id.editTextTextPassword)
        val refreshPassword = view.findViewById<TextView>(R.id.refreshPassword)

        val map = HashMap<String, String>()

        refreshPassword.setOnClickListener {
            (activity as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RefreshPasswordFragment())
                .addToBackStack(null)
                .commit()
        }

        loginButton.setOnClickListener {
            val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
            val auth: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_AUTH", Context.MODE_PRIVATE)
            val getName: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("GET_NAME", Context.MODE_PRIVATE)

            map["email"] = emailText.text.toString()
            map["password"] = passText.text.toString()

            val api = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRequests::class.java)

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val response = api.login("Bearer " + bearer.getString("access_token", "empty").toString(), map).awaitResponse()
                    val editor = auth.edit()
                    val editorName = getName.edit()
                    if (response.isSuccessful) {
                        editor.putBoolean("access_auth", true).apply()
                        editorName.putString("getName", response.body()?.data?.firstname + " " + response.body()?.data?.lastname).apply()
                        val intent = Intent((activity as FragmentActivity), MainActivity::class.java)
                        startActivity(intent)
                        Log.d("AuthStatus", "Authorized")
                    }
                    else {
                        when (response.code()) {
                            403 -> {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(activity as FragmentActivity, "Неверный email или пароль", Toast.LENGTH_SHORT).show()
                                }
                                Log.d("AuthStatus", "Unauthorized")
                            }
                            else -> Log.d("AuthStatus", response.code().toString())
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(activity as FragmentActivity, "No link internet", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}