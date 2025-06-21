package com.fancycolor.apk


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.fancycolor.apk.api.FilterGroup
import com.fancycolor.apk.ui.FilterDialogFragment
import kotlinx.android.synthetic.main.rowfiltermain.view.*


class FilterAdapter: RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    private var filterList = emptyList<FilterGroup>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        return FilterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rowfiltermain, parent, false))
    }

    override fun getItemCount(): Int {
        Log.d("Retrofit - getItemCount", filterList.size.toString())
        return filterList.size - 1
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        when (filterList[position].name) {
            "Цена" -> {
                Log.d("Continue", "Continue")
            }
            else -> {
                holder.itemView.nameText.text = filterList[position].name
                holder.itemView.valueText.text = "Выберите"
            }
        }
    }

    inner class FilterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.valueText.setOnClickListener {v: View ->
                val position: Int = adapterPosition
                val activity = v.context as AppCompatActivity

                val dialog = FilterDialogFragment(position)

                dialog.listener = object : FilterDialogFragment.FilterListener {
                    override fun pickFilter(name: String) {
                        itemView.valueText.text = name
                    }
                }
                dialog.show((activity as FragmentActivity).supportFragmentManager, "FilterDialogFragment")
            }
        }
    }

    fun setData(newList: List<FilterGroup>) {
        filterList = newList
        Log.d("Retrofit - ProductList", filterList.size.toString())
        Log.d("FilterData", filterList.toString())
        notifyDataSetChanged()
    }
}