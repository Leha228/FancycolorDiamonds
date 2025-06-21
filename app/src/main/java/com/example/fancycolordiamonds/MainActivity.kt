package com.example.fancycolordiamonds

import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Color.BLUE
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.IconCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fancycolordiamonds.ui.*
import com.fancycolor.apk.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_product.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), CommunicatorProduct {

    private val mainFragment = MainFragment()
    private val catalogFragment = CatalogFragment()
    private val backetFragment = BacketFragment()
    private val favoriteFragment = FavoriteFragment()
    private val profileFragment = ProfileFragment()

    override fun onStart() {
        super.onStart()
        getAuthStatus()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.itemIconTintList = null
        replaceFragment(mainFragment)

        val auth: SharedPreferences = getSharedPreferences("ACCESS_AUTH", Context.MODE_PRIVATE)
        if (auth.contains("access_auth")) {
            var badge = bottom_navigation.getOrCreateBadge(R.id.ic_menu_favorite)!!
            badge.isVisible = auth.getBoolean("access_auth", false)
            badge.backgroundColor = Color.BLUE
        }

        bottom_navigation.setOnNavigationItemSelectedListener {
            Log.d("ITEM", it.itemId.toString())
            when(it.itemId) {
                R.id.ic_menu_main -> {
                    replaceFragment(mainFragment)
                    it.setIcon(R.drawable.ic_main_active)
                }
                R.id.ic_menu_catalog -> {
                    replaceFragment(catalogFragment)
                    it.setIcon(R.drawable.ic_catalog_active)
                }
                R.id.ic_menu_backet -> {
                    replaceFragment(backetFragment)
                    it.setIcon(R.drawable.ic_backet_active)
                }
                R.id.ic_menu_favorite -> {
                    replaceFragment(favoriteFragment)
                    it.setIcon(R.drawable.ic_favorite_active)
                }
                R.id.ic_menu_lk -> {
                    replaceFragment(profileFragment)
                    it.setIcon(R.drawable.ic_lk_active)
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getDataCatalog(fragment: Fragment, key: Int) {
        val bundle = Bundle()
        val value: Array<String> = arrayOf("Новинки", "Кольца", "Серьги", "Ожирелья и подвески", "Браслеты", "Недавно проданные",
            "Кольца Солитер", "Кольца-HALO", "Кольца с тремя камнями", "Кольца с боковыми камнями", "Фантазийные кольца", "Бриллиантовые кольца", "Свадебные кольца", "Обручальные кольца", "Мужские кольца",
            "Бриллианты", "Камни")
        bundle.putString("query", value[key])
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun getAuthStatus() {
        val bearer: SharedPreferences = getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
        val auth: SharedPreferences = getSharedPreferences("ACCESS_AUTH", Context.MODE_PRIVATE)

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getAuthUser("Bearer " + bearer.getString("access_token", "empty").toString()).awaitResponse()
                val editor = auth.edit()
                if (response.isSuccessful) {
                    editor.putBoolean("access_auth", true).apply()
                    Log.d("AuthStatus", "Authorized")
                }
                else {
                    when (response.code()) {
                        403 -> {
                            editor.putBoolean("access_auth", false).apply()
                            Log.d("AuthStatus", "Unauthorized")
                        }
                        else -> Log.d("AuthStatus", response.code().toString())
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "No link internet", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
