package com.fancycolor.apk

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.core.view.ViewCompat
import com.fancycolor.apk.ui.ErrorInetFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://fancycolor.world"

class LaunchScreenActivity : AppCompatActivity() {
    override fun onStart() {
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_screen)

        val image = findViewById<ImageView>(R.id.imageView2)
        ViewCompat.animate(image)
            .translationY(-100f).duration = 1000

        getBearerId()
    }


    private fun getBearerId() {
        val sharedPref: SharedPreferences = getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
        val map = HashMap<String, String>()

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                if (sharedPref.contains("access_token")) {
                    map.put("old_token", sharedPref.getString("access_token", "empty").toString())
                    Log.d("Token", map.toString())
                    val response = api.getAuthBearerOldToken(map).awaitResponse()
                    if (response.isSuccessful) {
                        val data = response.body()!!
                        val editor = sharedPref.edit()
                        editor.putString("access_token", data.data.access_token).apply()
                        Log.d("Token", data.data.access_token)
                        val intent = Intent(this@LaunchScreenActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else {
                        Log.d("access_token", response.code().toString())
                    }
                }
                else {
                    val response = api.getAuthBearer().awaitResponse()
                    if (response.isSuccessful) {
                        val data = response.body()!!
                        val editor = sharedPref.edit()
                        editor.putString("access_token", data.data.access_token).apply()
                        Log.d("Token", data.data.access_token)
                        val intent = Intent(this@LaunchScreenActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else {
                        Log.d("access_token", response.code().toString())
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    //Toast.makeText(applicationContext, "No link internet", Toast.LENGTH_SHORT).show()
                    ErrorInetFragment().show(supportFragmentManager, null)
                }
            }
        }
    }
}
