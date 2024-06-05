package com.landa.ideacollector

import androidx.recyclerview.widget.DiffUtil

class MyDiffUtil(
    private val oldList: List<Idea>,
    private val newList: List<Idea>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> false
            oldList[oldItemPosition].idea != newList[newItemPosition].idea -> false
            oldList[oldItemPosition].date != newList[newItemPosition].date -> false
            oldList[oldItemPosition].priority != newList[newItemPosition].priority -> false
            else -> true
        }
    }
}