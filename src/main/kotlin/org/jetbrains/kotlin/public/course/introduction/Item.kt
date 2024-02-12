package org.jetbrains.kotlin.public.course.introduction

data class Item(val id: String, val info: String?) {
    fun loadInfo() = info
}

val items = listOf(
    Item("1", "Item#1"),
    Item("2", "Item#2"),
    Item("3", null)
)

fun findItem(id: String) = items.find{ it.id == id }
