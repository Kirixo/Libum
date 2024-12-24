package com.project.libum.domain.repository

import com.project.libum.data.dto.Book

interface BooksListRepository {
    suspend fun getUserBooksList(listID: Int, limit: Int?, page: Int?): List<Book>
    suspend fun getAllUserBooks(limit: Int?, page: Int?): List<Book>
    suspend fun getListsId(): HashMap<String, Int>?
}