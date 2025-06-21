package com.example.fancycolordiamonds

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.fancycolordiamonds.api.DataCountry
import com.example.fancycolordiamonds.ui.CountryRegisterFragment
import com.fancycolor.apk.R
import kotlinx.android.synthetic.main.rowcountryregister.view.*

class CountryAdapter(val onClick: (name : String, id : Int) -> Unit): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    private var countryList = emptyList<DataCountry>()
    private val dialogFragment = CountryRegisterFragment()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rowcountryregister, parent, false))
    }

    override fun getItemCount(): Int {
        Log.d("Retrofit - getItemCount", countryList.size.toString())
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.itemView.countryName.text = countryList[countryList.size - position - 1].name
    }

    inner class CountryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { v: View ->
                val position = adapterPosition
                val activity = getActivity(v.context)
                val countryName: SharedPreferences = activity.getSharedPreferences("COUNTRY_NAME", Context.MODE_PRIVATE)
                val countryID: SharedPreferences = activity.getSharedPreferences("COUNTRY_ID", Context.MODE_PRIVATE)
                val editorCountryName = countryName.edit()
                val editorCountryID = countryID.edit()

                //
                val name = countryList[countryList.size - position - 1].name
                val id = countryList[countryList.size - position - 1].countryId
                //

                editorCountryName.putString("CountryName", countryList[countryList.size - position - 1].name).apply()
                editorCountryID.putString("CountryID", countryList[countryList.size - position - 1].countryId.toString()).apply()

                onClick(name, id)
                //тут закрыть DialogFragment это в ui RegionRegisterFragment
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

    fun setData(newList: List<DataCountry>) {
        countryList = newList
        Log.d("Retrofit - ProductList", countryList.size.toString())
        notifyDataSetChanged()
    }
}