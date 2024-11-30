package com.project.libum.data.dto

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val isFavorite: Boolean,
)

enum class BookCategories{
    MyBooks,
    All,
}