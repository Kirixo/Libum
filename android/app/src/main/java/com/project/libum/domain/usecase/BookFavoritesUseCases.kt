package com.project.libum.domain.usecase

import android.util.Log
import com.project.libum.data.dto.Book

class BookFavoritesUseCases {
    fun addToFavorites(book: Book): Result<Unit>{
        Log.d("BookFavoritesUseCases", "addToFavorites: OK")
        book.isFavorite = true
        return Result.success(Unit)
    }

    fun deleteFromFavorites(book: Book): Result<Unit>{
        Log.d("BookFavoritesUseCases", "deleteToFavorites: OK")
        book.isFavorite = false
        return Result.success(Unit)
    }
}