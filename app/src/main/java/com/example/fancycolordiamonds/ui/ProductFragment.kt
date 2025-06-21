package com.example.fancycolordiamonds.ui

import android.annotation.SuppressLint
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
import kotlinx.android.synthetic.main.fragment_product.*
import com.example.fancycolordiamonds.*
import com.fancycolor.apk.R
import kotlinx.android.synthetic.main.fragment_product.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class ProductFragment : Fragment() {

    private val productAdapter by lazy { ProductAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_product, container, false)
        val btnSort = view.findViewById<Button>(R.id.buttonSortProduct)
        val text = arguments?.getString("query")
        view.product_text.setText(text)

        if (text == "Новинки") { getProductAll(64) }
        if (text == "Кольца") { getProductAll(65) }
        if (text == "Серьги") { getProductAll(66) }
        if (text == "Ожирелья и подвески") { getProductAll(67) }
        if (text == "Браслеты") { getProductAll(68) }
        if (text == "Недавно проданные") { getProductAll(91) }

        if (text == "Кольца Солитер") { getProductAll(75) }
        if (text == "Кольца-HALO") { getProductAll(76) }
        if (text == "Кольца с тремя камнями") { getProductAll(77) }
        if (text == "Кольца с боковыми камнями") { getProductAll(78) }
        if (text == "Фантазийные кольца") { getProductAll(80) }
        if (text == "Бриллиантовые кольца") { getProductAll(81) }
        if (text == "Свадебные кольца") { getProductAll(82) }
        if (text == "Обручальные кольца") { getProductAll(83) }
        if (text == "Мужские кольца") { getProductAll(84) }

        if (text == "Бриллианты") { getProductAll(59) }
        if (text == "Камни") { getProductAll(60) }

        btnSort.setOnClickListener {
            SortProductDialogFragment().show((activity as FragmentActivity).supportFragmentManager, null)
        }

        return view
    }

    @SuppressLint("SetTextI18n")
    private fun getProductAll(category: Int) {
        val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getProductAll("Bearer " + bearer.getString("access_token", "empty").toString(), category).awaitResponse()
                if (response.isSuccessful) {
                    Thread {
                        (activity as FragmentActivity).runOnUiThread(java.lang.Runnable {
                            if (recyclerViewProduct != null) {
                                recyclerViewProduct.adapter = productAdapter
                                recyclerViewProduct.layoutManager = GridLayoutManager(activity as FragmentActivity, 2)
                                countProduct.text = "Найдено товаров: " + response.body()?.data?.size.toString()
                                response.body()?.data?.let { productAdapter.setData(it) }
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
}