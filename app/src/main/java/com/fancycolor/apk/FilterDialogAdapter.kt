package com.fancycolor.apk

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fancycolor.apk.api.Filter
import kotlinx.android.synthetic.main.rowfilterdialog.view.*


class FilterDialogAdapter(val onClick: (name : String, id : String) -> Unit): RecyclerView.Adapter<FilterDialogAdapter.FilterDialogViewHolder>() {

    private var filterList = emptyList<Filter>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterDialogViewHolder {
        return FilterDialogViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rowfilterdialog, parent, false))
    }

    override fun getItemCount(): Int {
        Log.d("Retrofit - getItemCount", filterList.size.toString())
        return filterList.size
    }

    override fun onBindViewHolder(holder: FilterDialogViewHolder, position: Int) {
        holder.itemView.filterName.text = filterList[position].name
    }

    inner class FilterDialogViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.filterName.setOnClickListener { v: View ->
                val position = adapterPosition
                val activity = getActivity(v.context)

                val filters: SharedPreferences = activity.getSharedPreferences("FILTERS", Context.MODE_PRIVATE)
                val editor = filters.edit()
                val string =  filters.getString("filtersMain", "") + filterList[position].filterId + ","
                editor.putString("filtersMain", string).apply()

                val name = filterList[position].name
                val id = filterList[position].filterId
                onClick(name, id)
            }
        }
    }

    private fun getActivity(context: Context): Activity {
        return when (context) {
            is Activity -> context
            is ContextWrapper -> getActivity(context.baseContext)
            else -> error("Non Activity based context")
        }
    }

    fun setData(newList: List<Filter>) {
        filterList = newList
        Log.d("Retrofit - ProductList", filterList.size.toString())
        notifyDataSetChanged()
    }
}