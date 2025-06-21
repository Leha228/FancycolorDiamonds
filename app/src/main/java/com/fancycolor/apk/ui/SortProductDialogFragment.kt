package com.fancycolor.apk.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.fancycolor.apk.R


class SortProductDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sort_product_dialog, container, false)
        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
        val radioDef = view.findViewById<RadioButton>(R.id.radioButtonDefault)
        val radioPrMax = view.findViewById<RadioButton>(R.id.radioButtonPriceMax)
        val radioPrMin = view.findViewById<RadioButton>(R.id.radioButtonPriceMin)
        val radioNBegin = view.findViewById<RadioButton>(R.id.radioButtonPriceBegin)
        val radioNEmd = view.findViewById<RadioButton>(R.id.radioButtonPriceEnd)

        val sortProduct: SharedPreferences = (activity as FragmentActivity).getSharedPreferences("SORT_NAME", Context.MODE_PRIVATE)
        val editor = sortProduct.edit()

        if (sortProduct.contains("Sort")) {
            val key = sortProduct.getString("Sort", "Default")
            when (key) {
                "PriceMax" -> radioPrMax.isChecked = true
                "PriceMin" -> radioPrMin.isChecked = true
                "PriceBegin" -> radioNBegin.isChecked = true
                "PriceEnd" -> radioNEmd.isChecked = true
                "Default" -> radioDef.isChecked = true
            }
        }

        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { _, checkedId ->
            val radio = view.findViewById<RadioButton>(checkedId)

            if (radio == view.findViewById(R.id.radioButtonDefault)) {
                editor.putString("Sort", "Default").apply()
                radio.isChecked = true
                (activity as FragmentActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ProductFragment())
                    .commit()
                this.dismiss()

            }
            if (radio == view.findViewById(R.id.radioButtonPriceMax)) {
                editor.putString("Sort", "PriceMax").apply()
                radio.isChecked = true
                (activity as FragmentActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ProductFragment())
                    .commit()
                this.dismiss()
            }
            if (radio == view.findViewById(R.id.radioButtonPriceMin)) {
                editor.putString("Sort", "PriceMin").apply()
                radio.isChecked = true
                (activity as FragmentActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ProductFragment())
                    .commit()
                this.dismiss()
            }
            if (radio == view.findViewById(R.id.radioButtonPriceBegin)) {
                editor.putString("Sort", "PriceBegin").apply()
                radio.isChecked = true
                (activity as FragmentActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ProductFragment())
                    .commit()
                this.dismiss()
            }
            if (radio == view.findViewById(R.id.radioButtonPriceEnd)) {
                editor.putString("Sort", "PriceEnd").apply()
                radio.isChecked = true
                (activity as FragmentActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ProductFragment())
                    .commit()
                this.dismiss()
            }
        })


        return view
    }

}