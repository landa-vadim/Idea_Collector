package com.landa.ideacollector.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "items")
@Parcelize
data class Idea(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "priority")
    val priority: Priority,
    @ColumnInfo(name = "idea")
    val idea: String,
    @ColumnInfo(name = "date")
    val date: String
): Parcelable

enum class Priority {
    HIGH,
    MEDIUM,
    LOW
}
