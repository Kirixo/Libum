package com.project.libum.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.libum.data.dto.Book
import com.project.libum.data.dto.BookCategories
import com.project.libum.domain.repository.BooksListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityModel @Inject constructor(
    private val bookListRepository: BooksListRepository
): ViewModel() {

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    fun getBooksFromServer(){
        viewModelScope.launch {
            try {
                _books.postValue(bookListRepository.getBooksList())
                Log.d("Books", "getBooksFromServer: Normal")
            } catch (e: Exception) {
                Log.d("Books", "getBooksFromServer: Error")
                _books.value = emptyList()
            }
        }
    }


    fun updateBooks(){
        // TODO(Implement update)
        getBooksFromServer()
    }

    fun getBooksByCategory(category: BookCategories): List<Book>? {
        // TODO(Implement filtration by category)
        return _books.value
    }
}