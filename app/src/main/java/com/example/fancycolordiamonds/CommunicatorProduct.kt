package com.example.fancycolordiamonds

import androidx.fragment.app.Fragment

interface CommunicatorProduct {
    fun getDataCatalog(fragment: Fragment, key: Int)
}