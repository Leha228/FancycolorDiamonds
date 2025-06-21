package com.example.fancycolordiamonds

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
import com.example.fancycolordiamonds.api.DataFavorite
import com.example.fancycolordiamonds.ui.BacketFragment
import com.example.fancycolordiamonds.ui.ProductViewFragment
import com.fancycolor.apk.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rowbacket.view.*
import kotlinx.android.synthetic.main.rowfavorite.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var favoriteList = emptyList<DataFavorite>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rowfavorite, parent, false))
    }

    override fun getItemCount(): Int {
        Log.d("Retrofit - getItemCount", favoriteList.size.toString())
        return favoriteList.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        Picasso.get().load(favoriteList[position].thumb).into(holder.itemView.image_favorite)
        holder.itemView.price_favorite.text = favoriteList[position].price
        holder.itemView.title_favorite.text = favoriteList[position].name
    }

    inner class FavoriteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {v: View ->
                val position: Int = adapterPosition
                val data = favoriteList[position]
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
            itemView.btn_favorite.setOnClickListener {v: View ->
                val position: Int = adapterPosition
                val data = favoriteList[position]
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
        }
    }

    fun setData(newList: List<DataFavorite>) {
        favoriteList = newList
        Log.d("Retrofit - ProductList", favoriteList.size.toString())
        notifyDataSetChanged()
    }
}