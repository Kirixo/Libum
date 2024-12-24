package com.project.libum.domain.usecase

import com.project.libum.data.dto.Book
import com.project.libum.presentation.viewmodel.HomeViewModel

class BookFiltrationUseCase {
    fun execute(books: List<Book>, filters: HomeViewModel.BookCategories): List<Book> {
        // TODO(Not yet implemented)
        return books
    }
}
