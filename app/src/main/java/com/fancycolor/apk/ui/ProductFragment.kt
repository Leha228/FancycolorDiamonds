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
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_product.*
import com.fancycolor.apk.*
import com.fancycolor.apk.api.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.properties.Delegates

class ProductFragment : Fragment() {

    private val productAdapter by lazy { ProductAdapter() }
    private var pageNumber: Int = 1
    private var categoryId: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_product, container, false)
        val btnSort = view.findViewById<Button>(R.id.buttonSortProduct)
        val btnFilter = view.findViewById<Button>(R.id.buttonFilterProduct)
        val btnNext = view.findViewById<TextView>(R.id.nextBtn)
        val product_text = view.findViewById<TextView>(R.id.product_text)

        val category: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("CATEGORY", Context.MODE_PRIVATE)
        if (category.contains("category")) {
            product_text.text = category.getString("category", "empty")
        } else {
            product_text.text = arguments?.getString("query")
        }

        val text = product_text.text

        if (text == "Новинки") {
            getProductAll(64, 1)
            categoryId = 64
        }
        if (text == "Кольца") { getProductAll(65, 1)
            categoryId = 65
        }
        if (text == "Серьги") { getProductAll(66, 1)
            categoryId = 66
        }
        if (text == "Ожерелья и подвески") { getProductAll(91, 1)
            categoryId = 91
        }
        if (text == "Браслеты") { getProductAll(67, 1)
            categoryId = 67
        }
        if (text == "Недавно проданные") { getProductAll(68, 1)
            categoryId = 68
        }
        if (text == "C цветными бриллиантами") { getProductAll(70, 1)
            categoryId = 70
        }
        if (text == "С бриллиантами") { getProductAll(71, 1)
            categoryId = 71
        }
        if (text == "С драгоценными камнями") { getProductAll(72, 1)
            categoryId = 72
        }

        if (text == "Кольца Солитер") { getProductAll(75, 1)
            categoryId = 75
        }
        if (text == "Кольца-HALO") { getProductAll(76, 1)
            categoryId = 76
        }
        if (text == "Кольца с тремя камнями") { getProductAll(77, 1)
            categoryId = 77
        }
        if (text == "Кольца с боковыми камнями") { getProductAll(78, 1)
            categoryId = 78
        }
        if (text == "Фантазийные кольца") { getProductAll(80, 1)
            categoryId = 80
        }
        if (text == "Бриллиантовые кольца") { getProductAll(81, 1)
            categoryId = 81
        }
        if (text == "Свадебные кольца") { getProductAll(82, 1)
            categoryId = 82
        }
        if (text == "Обручальные кольца") { getProductAll(83, 1)
            categoryId = 83
        }
        if (text == "Мужские кольца") { getProductAll(84, 1)
            categoryId = 84
        }

        if (text == "Бриллианты") { getProductAll(59, 1)
            categoryId = 59
        }
        if (text == "Камни") { getProductAll(60, 1)
            categoryId = 60
        }


        btnSort.setOnClickListener {
            SortProductDialogFragment().show((activity as FragmentActivity).supportFragmentManager, null)
        }

        btnFilter.setOnClickListener {
            (activity as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProductFilterFragment())
                .addToBackStack(null)
                .commit()
        }

        btnNext.setOnClickListener {
            pageNumber++
            getProductAll(categoryId, pageNumber)
        }

        return view
    }

    private fun getProductAll(category: Int, page: Int) {
        val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
        val sortProduct: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("SORT_NAME", Context.MODE_PRIVATE)
        val categoryID: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("CATEGORY_ID", Context.MODE_PRIVATE)
        val filters: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("FILTERS", Context.MODE_PRIVATE)
        val editor = categoryID.edit()
        editor.putInt("category_id", category).apply()

        var string = filters.getString("filtersMain", "")
        if (string?.length!! > 0) {
            string?.substring(0, string.length - 1)
        }
        else {
            string = ""
        }
        Log.d("FilterID", string!!)

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                var response: Response<Product>? = null
                val key = sortProduct.getString("Sort", "Default")
                when (key) {
                    "PriceMax" -> response = api.getProductSort("Bearer " + bearer.getString("access_token", "empty").toString(), category, "price", "asc", string, page).awaitResponse()
                    "PriceMin" -> response = api.getProductSort("Bearer " + bearer.getString("access_token", "empty").toString(), category, "price", "desc", string, page).awaitResponse()
                    "PriceBegin" -> response = api.getProductSort("Bearer " + bearer.getString("access_token", "empty").toString(), category, "name", "asc", string, page).awaitResponse()
                    "PriceEnd" -> response = api.getProductSort("Bearer " + bearer.getString("access_token", "empty").toString(), category, "name", "desc", string, page).awaitResponse()
                    "Default" -> response = api.getProductAll("Bearer " + bearer.getString("access_token", "empty").toString(), category, string, page).awaitResponse()
                }
                Log.d("ResponseFilter", response.toString())
                if (response != null) {
                    if (response.isSuccessful) {
                        Thread {
                            (activity as FragmentActivity).runOnUiThread(java.lang.Runnable {
                                if (recyclerViewProduct != null && countProduct != null) {
                                    val position = page * 49
                                    recyclerViewProduct?.adapter = productAdapter
                                    recyclerViewProduct?.layoutManager = GridLayoutManager(activity as FragmentActivity, 2)
                                    countProduct?.text = "Найдено товаров: " + response.body()?.data?.size.toString()
                                    if (response.body()?.data?.size!! < 49) {
                                        nextBtn.visibility = View.GONE
                                        response.body()?.data?.let { productAdapter?.setData(it, page)}
                                        if (page > 1) {
                                            recyclerViewProduct?.scrollToPosition(position - 50 + response.body()?.data?.size!!)
                                        }
                                    } else
                                    {
                                        response.body()?.data?.let { productAdapter?.setData(it, page) }
                                        if (page > 1) {
                                            recyclerViewProduct?.scrollToPosition(position - 51)
                                        }
                                        nextBtn?.visibility = View.VISIBLE
                                    }
                                }
                            })
                        }.start()
                        Log.d("Retrofit", "Success")
                    } else {
                        Log.d("Retrofit", response.code().toString())
                    }
                }
            } catch (e: Exception) {

            }
        }

        val editorFilter = filters.edit()
        editorFilter.putString("filtersMain", "").apply()
    }
}