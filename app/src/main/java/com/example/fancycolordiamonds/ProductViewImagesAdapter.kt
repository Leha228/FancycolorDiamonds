package com.example.fancycolordiamonds

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fancycolordiamonds.api.DataProductView
import com.fancycolor.apk.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rowviewproductimages.view.*

class ProductViewImagesAdapter: RecyclerView.Adapter<ProductViewImagesAdapter.ProductViewImagesViewHolder>() {

    private var productList = emptyList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewImagesViewHolder {
        return ProductViewImagesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rowviewproductimages, parent, false))
    }

    override fun getItemCount(): Int {
        Log.d("Retrofit - Images", productList.size.toString())
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewImagesViewHolder, position: Int) {
        Picasso.get().load(productList[position]).into(holder.itemView.product_image)
    }

    inner class ProductViewImagesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    fun setData(newList: List<String>) {
        productList = newList
        Log.d("Retrofit - Images", productList.size.toString())
        notifyDataSetChanged()
    }

}
