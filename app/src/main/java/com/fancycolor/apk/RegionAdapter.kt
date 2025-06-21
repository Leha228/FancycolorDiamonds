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
import com.fancycolor.apk.api.Zone

import kotlinx.android.synthetic.main.rowregionregister.view.*

class RegionAdapter(val onClick: (name : String, id : String) -> Unit): RecyclerView.Adapter<RegionAdapter.RegionViewHolder>() {

    private var regionList = emptyList<Zone>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder {
        return RegionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rowregionregister, parent, false))
    }

    override fun getItemCount(): Int {
        Log.d("Retrofit - getItemCount", regionList.size.toString())
        return regionList.size
    }

    override fun onBindViewHolder(holder: RegionViewHolder, position: Int) {
        holder.itemView.regionName.text = regionList[regionList.size - position - 1].name
    }

    inner class RegionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { v: View ->
                val position = adapterPosition
                val activity = getActivity(v.context)
                val regionName: SharedPreferences = activity.getSharedPreferences("REGION_NAME", Context.MODE_PRIVATE)
                val regionID: SharedPreferences = activity.getSharedPreferences("REGION_ID", Context.MODE_PRIVATE)
                val editorRegionName = regionName.edit()
                val editorRegionID = regionID.edit()

                //
                val name = regionList[regionList.size - position - 1].name
                val countryID = regionList[regionList.size - position - 1].countryId
                //

                editorRegionName.putString("RegionName", regionList[regionList.size - position - 1].name).apply()
                editorRegionID.putString("RegionID", regionList[regionList.size - position - 1].countryId).apply()
                onClick(name, countryID)
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

    fun setData(newList: List<Zone>) {
        regionList = newList
        Log.d("Retrofit - RegionList", regionList.size.toString())
        notifyDataSetChanged()
    }
}