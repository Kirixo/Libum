package com.project.libum.domain.repository

import com.project.libum.data.dto.Book
import com.project.libum.data.model.BookListResponse
import retrofit2.Response

class BooksListRepositoryImpl: BooksListRepository {
    override suspend fun getBooksList(): List<Book> {
        // TODO("Not yet implemented")
        return listOf(Book(2, "Biblia", "allah"),
            Book(2, "Biblia", "allah"),
            Book(2, "Biblia", "allah"),
            Book(2, "Biblia", "allah"))
    }
}