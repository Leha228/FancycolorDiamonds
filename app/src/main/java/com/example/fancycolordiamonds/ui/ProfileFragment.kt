package com.example.fancycolordiamonds.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import com.example.fancycolordiamonds.ApiRequests
import com.example.fancycolordiamonds.BASE_URL
import com.fancycolor.apk.R
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val login = view.findViewById<Button>(R.id.login)
        val register = view.findViewById<Button>(R.id.register)
        val mainContainer = view.findViewById<ConstraintLayout>(R.id.constraintLayoutMenu)

        val auth: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_AUTH", Context.MODE_PRIVATE)

        if (auth.contains("access_auth")) {
            if (auth.getBoolean("access_auth", false)) {
                mainContainer.visibility = View.VISIBLE
                login.visibility = View.GONE
                register.visibility = View.GONE
            }
            else {
                mainContainer.visibility = View.GONE
                login.visibility = View.VISIBLE
                register.visibility = View.VISIBLE
            }
        }
        else {
            Log.d("FavoriteFragment", "No access_auth shared")
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

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button2.setOnClickListener { view ->
            replaceFragment(ProfileSettingRenameContactNameFragment())
        }

        button3.setOnClickListener { view ->
            replaceFragment(ProfileSettingPasswordFragment())
        }

        button7.setOnClickListener { view ->
            replaceFragment(ProfileEmailDistribFragment())
        }

        button8.setOnClickListener { view ->
            logout()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        (activity as FragmentActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun logout() {
        val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
        val auth: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_AUTH", Context.MODE_PRIVATE)

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.logout("Bearer " + bearer.getString("access_token", "empty").toString()).awaitResponse()
                val editor = auth.edit()
                if (response.isSuccessful) {
                    editor.putBoolean("access_auth", false).apply()
                    (activity as FragmentActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MainFragment())
                        .addToBackStack(null)
                        .commit()
                    Log.d("AuthStatus", "Unauthorized")
                } else {
                    Log.d("AuthStatus", response.code().toString())
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(activity as FragmentActivity, "No link internet", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}