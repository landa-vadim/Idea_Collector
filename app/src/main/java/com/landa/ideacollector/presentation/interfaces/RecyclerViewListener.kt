package com.landa.ideacollector.presentation.interfaces

import com.landa.ideacollector.domain.model.Idea

interface RecyclerViewListener {
    fun onLongClick(idea: Idea)
}