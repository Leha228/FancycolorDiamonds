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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.fancycolor.apk.*
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_favorite.buttonCatalog
import kotlinx.android.synthetic.main.fragment_favorite.textEmpty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class FavoriteFragment : Fragment() {

    private val favoriteAdapter by lazy { FavoriteAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        val login = view.findViewById<Button>(R.id.login)
        val register = view.findViewById<Button>(R.id.register)
        val mainContainer = view.findViewById<ConstraintLayout>(R.id.constraintLayoutCard)
        val btnCatalog = view.findViewById<Button>(R.id.buttonCatalog)

        val auth: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_AUTH", Context.MODE_PRIVATE)

        if (auth.contains("access_auth")) {
            if (auth.getBoolean("access_auth", false)) {
                mainContainer.visibility = View.VISIBLE
                login.visibility = View.GONE
                register.visibility = View.GONE
                getProductFavoriteAll()
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

        btnCatalog.setOnClickListener {
            (activity as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CatalogFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }

    private fun getProductFavoriteAll() {
        val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getProductFavorite("Bearer " + bearer.getString("access_token", "empty").toString()).awaitResponse()
                if (response.isSuccessful) {
                    Thread {
                        (activity as FragmentActivity).runOnUiThread(java.lang.Runnable {
                            if (recyclerViewFavorite != null) {
                                if (response.body()?.data?.size == 0) {
                                    textEmpty?.visibility = View.VISIBLE
                                    buttonCatalog?.visibility = View.VISIBLE
                                }
                                else {
                                    recyclerViewFavorite.adapter = favoriteAdapter
                                    recyclerViewFavorite.layoutManager = GridLayoutManager(activity as FragmentActivity, 1)
                                    response.body()?.data?.let { favoriteAdapter.setData(it) }
                                }
                            }
                        })
                    }.start()
                    Log.d("Retrofit", "Success")
                }
                else {
                    Log.d("Retrofit", response.code().toString())
                }
            } catch (e: Exception) {
                Thread {
                    (activity as FragmentActivity).runOnUiThread(java.lang.Runnable {
                        if (recyclerViewFavorite != null) {
                            recyclerViewFavorite.visibility = View.GONE
                            textEmpty.visibility = View.VISIBLE
                            buttonCatalog.visibility = View.VISIBLE
                        }
                    })
                }.start()
            }
        }
    }

}