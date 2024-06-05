package com.landa.ideacollector

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "items")
data class Item(
@PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "priority")
    val priority: Priority,
    @ColumnInfo(name = "idea")
    val idea: String,
    @ColumnInfo(name = "date")
    val date: String
)
