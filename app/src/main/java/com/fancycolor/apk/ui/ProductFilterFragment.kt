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
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.fancycolor.apk.*
import kotlinx.android.synthetic.main.fragment_product_filter.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory


class ProductFilterFragment : Fragment() {

    private val filterAdapter by lazy { FilterAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view  = inflater.inflate(R.layout.fragment_product_filter, container, false)
        val btn = view.findViewById<Button>(R.id.buttonLookProduct)

        val category: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("CATEGORY_ID", Context.MODE_PRIVATE)
        if (category.contains("category_id")) {
            getFilter(category.getInt("category_id", 1), 1)
        }

        btn.setOnClickListener {
            (activity as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProductFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }

    private fun getFilter(id: Int, page: Int) {
        val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        Log.d("ID_CATEGORY", id.toString())

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getProductFilter("Bearer " + bearer.getString("access_token", "empty").toString(), id, page).awaitResponse()
                if (response.isSuccessful) {
                    val data = response.body()!!
                    Thread {
                        (activity as FragmentActivity).runOnUiThread(java.lang.Runnable {
                            if (recyclerViewFilterProductMain != null) {
                                if (data.data.filters.filterGroups.isEmpty()) {
                                    emptyText.visibility = View.VISIBLE
                                }
                                else {
                                    recyclerViewFilterProductMain.adapter = filterAdapter
                                    recyclerViewFilterProductMain.layoutManager = GridLayoutManager(activity as FragmentActivity, 1)
                                    filterAdapter.setData(data.data.filters.filterGroups)
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
                Log.d("Error", "No link connect")
            }
        }
    }
}