package com.project.libum.domain.repository

import android.util.Log
import com.project.libum.data.dto.Book
import com.project.libum.data.local.dao.BookDao
import javax.inject.Inject

class BooksListRepositoryImpl @Inject constructor(
    private val bookDao: BookDao
) : BooksListRepository {
    override suspend fun getBooksList(): List<Book> {
        // TODO("Not yet implemented")
        val bookList = mutableListOf(
            Book(1, "First book", "First author", true, -1),
            Book(2, "Second Book", "Second author", false, -1),
            Book(3, "Third book", "Third author", true, -1),
            Book(4, "Fourth book", "Fourth author", true, -1),
            Book(5, "Fifth book", "Fifth author", true, -1),
            Book(5, "Fifth book", "Fifth author", true, -1),
            Book(5, "Fifth book", "Fifth author", true, -1),
            Book(5, "Fifth book", "Fifth author", true, -1),
            Book(5, "Fifth book", "Fifth author", true, -1),
            Book(5, "Fifth book", "Fifth author", true, -1),
            Book(5, "Fifth book", "Fifth author", true, -1),
            Book(5, "Fifth book", "Fifth author", true, -1),
            Book(5, "Fifth book", "Fifth author", true, -1),
            Book(5, "Fifth book", "Fifth author", true, -1),
            Book(5, "Fifth book", "Fifth author", true, -1),
            Book(5, "Fifth book", "Fifth author", true, -1),
            Book(5, "Fifth book", "Fifth author", true, -1),
            Book(5, "Fifth book", "Fifth author", true, -1),
            Book(5, "Fifth book", "Fifth author", true, -1),
        )

        return getValidatedBookList(bookList)
    }

    private suspend fun getValidatedBookList(bookList: List<Book>): List<Book> {
        return bookList.map { book ->
            if (book.percentRead == -1 || book.localPercentRead == null) {
                val localBook = bookDao.getBook(book.id)
                if (localBook != null && localBook.lastReadPage > 0) {
                    book.copy(
                        percentRead = localBook.readPercent,
                        localPercentRead = localBook.readPercent,
                        lastReadPage = localBook.lastReadPage
                    )
                } else {
                    book
                }
            } else {
                book
            }
        }
    }

}