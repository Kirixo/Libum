package com.project.libum.domain.repository

import com.project.libum.data.dto.Book
import com.project.libum.data.model.BookListResponse
import retrofit2.Response

interface BooksListRepository {
    suspend fun getBooksList(): List<Book>
}