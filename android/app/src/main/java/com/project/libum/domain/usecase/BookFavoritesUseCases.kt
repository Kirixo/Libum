package com.project.libum.domain.usecase

import android.util.Log
import com.project.libum.data.dto.Book

class BookFavoritesUseCases {
    fun addToFavorites(book: Book){
        Log.d("BookFavoritesUseCases", "addToFavorites: OK")
    }

    fun deleteFromFavorites(book: Book){
        Log.d("BookFavoritesUseCases", "deleteToFavorites: OK")
    }
}