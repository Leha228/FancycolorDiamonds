package com.fancycolor.apk

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fancycolor.apk.api.DataX
import com.fancycolor.apk.ui.ProductViewFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row.view.*

class ProductAdapter: RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var productList = emptyList<DataX>()
    private lateinit var mDiffResult: DiffUtil.DiffResult

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
                viewFragment.arguments = bundle
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, viewFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    fun setData(newList: List<DataX>, page: Int) {
        mDiffResult = DiffUtil.calculateDiff(DiffUtilCallback(productList, newList))
        mDiffResult.dispatchUpdatesTo(this)
        productList = productList + newList
    }
}
