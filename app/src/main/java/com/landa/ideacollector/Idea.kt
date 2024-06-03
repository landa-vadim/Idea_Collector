package com.landa.ideacollector

data class Idea(
    val ideasPriority: Priority,
    val ideasText: String,
    val ideasDate: String,
)

enum class Priority {
    HIGH,
    MEDIUM,
    LOW;
}