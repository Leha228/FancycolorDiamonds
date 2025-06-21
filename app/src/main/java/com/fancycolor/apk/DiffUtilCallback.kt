package com.fancycolor.apk

import androidx.recyclerview.widget.DiffUtil
import com.fancycolor.apk.api.DataX

class DiffUtilCallback(
    private val newList: List<DataX>,
    private val oldList: List<DataX>
):DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition] == newList[newItemPosition]
}