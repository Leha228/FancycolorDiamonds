package com.example.fancycolordiamonds

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.fancycolordiamonds.api.DataX
import com.example.fancycolordiamonds.api.Product
import com.example.fancycolordiamonds.ui.ProductViewFragment
import com.fancycolor.apk.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row.view.*
import kotlinx.android.synthetic.main.rowfavorite.view.*

class ProductAdapter: RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var productList = emptyList<DataX>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false))
    }

    override fun getItemCount(): Int {
        Log.d("Retrofit - getItemCount", productList.size.toString())
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        Picasso.get().load(productList[position].image).into(holder.itemView.img_product)
        holder.itemView.price_product.text = productList[position].priceFormated
        holder.itemView.title_product.text = productList[position].name
    }

    inner class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {v: View ->
                val position: Int = adapterPosition
                val data = productList[position]
                val activity = v.context as AppCompatActivity
                val viewFragment = ProductViewFragment()
                val bundle = Bundle()
                bundle.putInt("id", productList[position].id)
                viewFragment.setArguments(bundle)
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, viewFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    fun setData(newList: List<DataX>) {
        productList = newList
        Log.d("Retrofit - ProductList", productList.size.toString())
        notifyDataSetChanged()
    }
}