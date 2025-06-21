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
import androidx.recyclerview.widget.RecyclerView
import com.fancycolor.apk.api.ProductX
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

class CartPayAdapter: RecyclerView.Adapter<CartPayAdapter.CartViewHolder>() {

    private var productList = emptyList<ProductX>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rowtableproductpay, parent, false))
    }

    override fun getItemCount(): Int {
        Log.d("Retrofit - getItemCount", productList.size.toString())
        return productList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        Picasso.get().load(productList[position].thumb).into(holder.itemView.payImage)
        holder.itemView.payPrice.text = "Цена за единицу: " + productList[position].price
        holder.itemView.payTitle.text = productList[position].name
        holder.itemView.payCount.text = "Количество: " + productList[position].quantity
        holder.itemView.payPriceAll.text = "Всего: " + productList[position].price
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

                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        val response = api.cartAdd("Bearer " + bearer.getString("access_token", "empty").toString(), map).awaitResponse()
                        if (response.isSuccessful) {
                            data.quantity = (data.quantity.toInt() + 1).toString()
                            Log.d("Retrofit", "Добавили +1 кол-во товара")
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
                notifyDataSetChanged()
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

                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        val response = api.cartDelete("Bearer " + bearer.getString("access_token", "empty").toString(), data.key).awaitResponse()
                        if (response.isSuccessful) {
                            data.quantity = (data.quantity.toInt() - 1).toString()
                            Log.d("Retrofit", "Удалили одно кол-во товара")
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
                notifyDataSetChanged()
            }
        }
    }

    fun setData(newList: List<ProductX>) {
        productList = newList
        Log.d("List init", productList.toString())
        notifyDataSetChanged()
        Log.d("Retrofit - ProductList", productList.size.toString())
    }
}