package com.project.libum.domain.repository

import com.project.libum.data.dto.Book
import com.project.libum.data.local.dao.BookDao
import com.project.libum.data.local.dao.UserDao
import com.project.libum.data.model.toParcelableBook
import com.project.libum.data.remote.ApiClient.apiService
import javax.inject.Inject

class BooksListRepositoryImpl @Inject constructor(
    private val bookDao: BookDao,
    private val userDao: UserDao,
) : BooksListRepository {

    override suspend fun getUserBooksList(listID: Int, limit: Int?, page: Int?): List<Book> {
        val user = userDao.getUser()
        val response = apiService.getBooksList(
            user!!.id.toString(),
            listID.toString(),
            limit,
            page
        )

        if (response.isSuccessful){
            return getValidatedBookList(response.body()!!.responseBooks.map {
                it.toParcelableBook()
            })
        }

        return emptyList()
    }

    override suspend fun getAllUserBooks(limit: Int?, page: Int?): List<Book> {
        val response = apiService.getUserBooksList(
            limit?: 25,
            page?: 1
        )

        if (response.isSuccessful){
            return getValidatedBookList(response.body()!!.books.map {
                it.toParcelableBook()
            })
        }

        return emptyList()
    }

    override suspend fun getListsId(): HashMap<String, Int>? {
        val response = apiService.getListsId()

        if (response.isSuccessful){
            val hashMap = HashMap<String, Int>()
            response.body()!!.map {
                hashMap.put(it.name, it.id)
            }
            return hashMap
        }

        return null
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
                    book.copy(
                        percentRead = null
                    )
                }
            } else {
                book
            }
        }
    }

}