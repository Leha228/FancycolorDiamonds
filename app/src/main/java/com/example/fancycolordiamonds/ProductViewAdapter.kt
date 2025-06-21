package com.example.fancycolordiamonds

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.fancycolordiamonds.api.AttributeX
import com.example.fancycolordiamonds.api.DataX
import com.example.fancycolordiamonds.ui.ProductViewFragment
import com.fancycolor.apk.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row.view.*
import kotlinx.android.synthetic.main.rowviewproduct.view.*

class ProductViewAdapter: RecyclerView.Adapter<ProductViewAdapter.ProductViewViewHolder>() {

    private var productList = emptyList<AttributeX>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewViewHolder {
        return ProductViewViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rowviewproduct, parent, false))
    }

    override fun getItemCount(): Int {
        Log.d("Retrofit - getItemCount", productList.size.toString())
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewViewHolder, position: Int) {
        holder.itemView.title_character.text = productList[position].name
        holder.itemView.name_character.text = productList[position].text
    }

    inner class ProductViewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    fun setData(newList: List<AttributeX>) {
        productList = newList
        Log.d("Retrofit - ProductList", productList.size.toString())
        notifyDataSetChanged()
    }

}