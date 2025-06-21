package com.fancycolor.apk.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.fancycolor.apk.*
import kotlinx.android.synthetic.main.fragment_product_view.*
import kotlinx.android.synthetic.main.rowviewproductimages.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory


class ProductViewFragment : Fragment() {

    private val productViewAdapter by lazy { ProductViewAdapter() }
    private val productViewImageAdapter by lazy { ProductViewImagesAdapter() }
    private val shareBundle: Bundle =  Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id: Int
        val bundle = arguments
        if (bundle != null) {
            id = bundle.getInt("id")
            getProduct(id)
            getFavorite(id)
        }

        add_backet.setOnClickListener {
            if (bundle != null) {
                val id = bundle.getInt("id")
                getProductToBacket(id)
            }
        }

        phoneNumber.setOnClickListener {
            val intent = Intent(Intent.ACTION_CALL);
            intent.data = Uri.parse("tel:88005008396")
            if (ActivityCompat.checkSelfPermission((activity as FragmentActivity), android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(intent)
            }
            else {
                ActivityCompat.requestPermissions((activity as FragmentActivity), arrayOf(android.Manifest.permission.CALL_PHONE), 1)
            }
        }

        mapClick.setOnClickListener {
            val geoURI = "geo:55.74818283465319,37.540347225547904?z=15"
            val geo = Uri.parse(geoURI)
            val geoIntent = Intent(Intent.ACTION_VIEW, geo)
            startActivity(geoIntent)
        }

        emailSend.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_APP_EMAIL)
            startActivity(intent)
        }

        btn_share.setOnClickListener {
            val fragment = ShareDialogFragment()
            fragment.arguments = shareBundle
            (activity as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        add_favorite.setOnClickListener {
            if (bundle != null) {
                val id = bundle.getInt("id")
                if (add_favorite.text == "Добавить в избранное") {
                    getProductToFavorite(id)
                }
                else {
                    getProductRemoveFavorite(id)
                }
            }
        }

    }

    private fun getFavorite(id:Int) {
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
                    val data = response.body()!!.data
                    Log.d("Wishlist", data.size.toString())

                    for (dat in data) {
                        Log.d("DATA", dat.productId.toString())
                        if (dat.productId == id.toString()) {
                            add_favorite?.text = "Удалить с избранного"
                        }
                    }

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

    private fun getProduct(id:Int) {
        val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getProductView("Bearer " + bearer.getString("access_token", "empty").toString(), id).awaitResponse()
                if (response.isSuccessful) {
                    val data = response.body()!!
                    Thread {
                        (activity as FragmentActivity).runOnUiThread(java.lang.Runnable {
                            if (recyclerViewProductsViewImage != null && recyclerViewProductsView != null) {
                                recyclerViewProductsViewImage.adapter = productViewImageAdapter
                                recyclerViewProductsViewImage.layoutManager = GridLayoutManager(activity as FragmentActivity, 1, RecyclerView.HORIZONTAL, false)

                                indicator.attachToRecyclerView(recyclerViewProductsViewImage)
                                var shapHelper = PagerSnapHelper()
                                shapHelper.attachToRecyclerView(recyclerViewProductsViewImage)

                                shareBundle?.putString("img", data.data.image)
                                shareBundle?.putString("name", data.data.name)

                                data.data.images.let { productViewImageAdapter.setData(it) }
                                price_product_view.text = data.data.priceFormated
                                product_text_view.text = data.data.name
                                product_description_view.text = data.data.description
                                recyclerViewProductsView.adapter = productViewAdapter
                                recyclerViewProductsView.layoutManager = GridLayoutManager(activity as FragmentActivity, 1)
                                data.data.attributeGroups[0].attribute.let { productViewAdapter.setData(it) }
                                constraintLayoutToFavorite.visibility = View.VISIBLE
                                constraintLayoutProductPrice.visibility = View.VISIBLE
                                constraintLayoutToBacket.visibility = View.VISIBLE
                                constraintLayoutInfoProduct.visibility = View.VISIBLE
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

    private fun getProductToBacket(id:Int) {
        val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        val map = HashMap<String, String>()
        map.put("product_id", id.toString())
        map.put("quantity", 1.toString())

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.cartAdd("Bearer " + bearer.getString("access_token", "empty").toString(), map).awaitResponse()
                if (response.isSuccessful) {
                    val data = response.body()!!
                    withContext(Dispatchers.Main) {
                        Toast.makeText(activity as FragmentActivity, "Товар успешно добавлен!", Toast.LENGTH_SHORT).show()
                    }
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

    private fun getProductToFavorite(id:Int) {
        val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.favoriteAdd("Bearer " + bearer.getString("access_token", "empty").toString(), id).awaitResponse()
                if (response.isSuccessful) {
                    val data = response.body()!!
                    withContext(Dispatchers.Main) {
                        Toast.makeText(activity as FragmentActivity, "Товар успешно добавлен!", Toast.LENGTH_SHORT).show()
                        add_favorite?.text = "Удалить с избранного"
                    }
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

    private fun getProductRemoveFavorite(id:Int) {
        val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.favoriteDelete("Bearer " + bearer.getString("access_token", "empty").toString(), id).awaitResponse()
                if (response.isSuccessful) {
                    (activity as FragmentActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, FavoriteFragment())
                        .addToBackStack(null)
                        .commit()
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