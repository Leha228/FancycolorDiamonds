package com.fancycolor.apk

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.fancycolor.apk.api.ProductX
import com.fancycolor.apk.ui.BacketFragment
import com.fancycolor.apk.ui.PayFragment
import com.fancycolor.apk.ui.ProductViewFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rowtableproductpay.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import kotlin.collections.HashMap

class CartPayAdapter: RecyclerView.Adapter<CartPayAdapter.CartViewHolder>() {

    private var productList = emptyList<ProductX>()
    private var totalPrice: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rowtableproductpay, parent, false))
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        Picasso.get().load(productList[position].thumb).into(holder.itemView.payImage)
        holder.itemView.payPrice.text = "Цена за единицу: " + productList[position].price
        holder.itemView.payTitle.text = productList[position].name
        holder.itemView.payCount.text = "Количество: " + productList[position].quantity
        holder.itemView.payPriceAll.text = "Всего: " + productList[position].total
    }

    inner class CartViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {v: View ->
                val position: Int = adapterPosition
                Log.d("Position", position.toString())
                val data = productList[position]
                val activity = v.context as AppCompatActivity
                val viewFragment = ProductViewFragment()
                val bundle = Bundle()
                bundle.putInt("id", data.productId.toInt())
                viewFragment.setArguments(bundle)
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, viewFragment)
                    .addToBackStack(null)
                    .commit()
            }
            itemView.buttonAdd.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                val data = productList[position]
                val activity = v.context as AppCompatActivity

                val bearer: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
                val api = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiRequests::class.java)

                val map = HashMap<String, String>()
                map.put("product_id", data.productId)
                map.put("quantity", 1.toString())

                GlobalScope.launch(Dispatchers.Main) {
                    try {
                        val response = api.cartAdd("Bearer " + bearer.getString("access_token", "empty").toString(), map).awaitResponse()
                        if (response.isSuccessful) {
                            data.quantity = (data.quantity.toInt() + 1).toString()
                            val total: Int = data.total.replace(Regex("[^0-9]"), "").toInt()
                            val price: Int = data.price.replace(Regex("[^0-9]"), "").toInt()
                            totalPrice = "${separateWithSpaces(total + price)}p."
                            data.total = totalPrice
                            notifyDataSetChanged()
                        }
                        else {
                            Log.d("Retrofit", response.code().toString())
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(activity as FragmentActivity, "Ошибка добавления товара", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }

            itemView.buttonRemove.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                val data = productList[position]
                val activity = v.context as AppCompatActivity

                val bearer: SharedPreferences = activity.getSharedPreferences("ACCESS_TOKEN", Context.MODE_PRIVATE)
                val api = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiRequests::class.java)

                GlobalScope.launch(Dispatchers.Main) {
                    try {
                        val response = api.cartDelete("Bearer " + bearer.getString("access_token", "empty").toString(), data.key).awaitResponse()
                        if (response.isSuccessful) {
                            if (data.quantity != "1") {
                                data.quantity = (data.quantity.toInt() - 1).toString()
                                val total: Int = data.total.replace(Regex("[^0-9]"), "").toInt()
                                val price: Int = data.price.replace(Regex("[^0-9]"), "").toInt()
                                totalPrice = "${separateWithSpaces(total - price)}p."
                                data.total = totalPrice
                                notifyDataSetChanged()
                            }
                            else {
                                (activity as FragmentActivity).supportFragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, BacketFragment())
                                    .addToBackStack(null)
                                    .commit()
                            }
                        }
                        else {
                            Log.d("Retrofit", response.code().toString())
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(activity as FragmentActivity, "Ошибка удаления товара", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    fun setData(newList: List<ProductX>) {
        productList = newList
        notifyDataSetChanged()
    }

    fun getTotalPrice(): String {
        return totalPrice
    }

    fun separateWithSpaces(amount: Int): String {
        val decimalFormat = DecimalFormat("#,###", DecimalFormatSymbols(Locale("ru", "RU")))
        return decimalFormat.format(amount)
    }
}