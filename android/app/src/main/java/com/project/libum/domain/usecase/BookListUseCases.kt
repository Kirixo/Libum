package com.project.libum.domain.usecase

import com.project.libum.data.dto.Book
import com.project.libum.domain.repository.BooksListRepository
import com.project.libum.presentation.viewmodel.HomeViewModel.BookCategories
import com.project.libum.presentation.viewmodel.HomeViewModel.BookCategories.Archive
import com.project.libum.presentation.viewmodel.HomeViewModel.BookCategories.Complete
import com.project.libum.presentation.viewmodel.HomeViewModel.BookCategories.Reading

class BookListUseCases(
    private val bookRepository: BooksListRepository
) {

    suspend fun getBooksList(listID: Int, limit: Int?, page: Int?): List<Book>{
        return bookRepository.getUserBooksList(listID, limit, page)
    }

    suspend fun getAllBooks(limit: Int?, page: Int?): List<Book>{
        return bookRepository.getAllUserBooks(limit, page)
    }

    suspend fun getCategories(): HashMap<BookCategories, Int>{
        val hashMap = HashMap<BookCategories, Int>()

        bookRepository.getListsId()?.map { pair ->
            get(pair.key)?.let { category ->
                hashMap.put(category, pair.value)
            }
        }

        return hashMap
    }

    fun get(name: String): BookCategories?{
        return when(name.lowercase()){
            "complete" -> Complete
            "archived" -> Archive
            "Reading" -> Reading
            else -> null
        }
    }

}