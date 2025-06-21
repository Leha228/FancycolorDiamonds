package com.fancycolor.apk.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.fancycolor.apk.*
import kotlinx.android.synthetic.main.fragment_filter_dialog.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory


class FilterDialogFragment(position: Int) : DialogFragment() {

    private val filterAdapter by lazy { FilterDialogAdapter(onClick = { name, id -> onClick(name, id) }) }
    lateinit var listener: FilterDialogFragment.FilterListener
    private val position: Int = position

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_filter_dialog, container, false)
        val btnLookProduct = view.findViewById<Button>(R.id.buttonLookProduct)
        val category: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("CATEGORY_ID", Context.MODE_PRIVATE)
        if (category.contains("category_id")) {
            getFilter(category.getInt("category_id", 1), 1)
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
                            if (recyclerViewFilter != null) {
                                recyclerViewFilter.adapter = filterAdapter
                                recyclerViewFilter.layoutManager = GridLayoutManager(activity as FragmentActivity, 1)
                                filterAdapter.setData(data.data.filters.filterGroups[position].filter)
                            }
                        })
                    }.start()
                    Log.d("Retrofit", "Success")
                }
                else {
                    Log.d("Retrofit", response.code().toString())
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(activity as FragmentActivity, "No link internet", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun onClick(name : String, id : String) {
        listener.pickFilter(name)
        this.dismiss()
    }

    interface FilterListener {
        fun pickFilter(name : String)
    }

}