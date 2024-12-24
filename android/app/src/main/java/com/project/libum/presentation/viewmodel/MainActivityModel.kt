package com.project.libum.presentation.viewmodel

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.libum.data.dto.Book
import com.project.libum.domain.repository.BooksListRepository
import com.project.libum.domain.usecase.BookFavoritesUseCases
import com.project.libum.domain.usecase.BookListUseCases
import com.project.libum.presentation.view.custom.BookView
import com.project.libum.presentation.viewmodel.HomeViewModel.BookCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityModel @Inject constructor(
    private val bookListUseCases: BookListUseCases,
    private val bookFavoritesUseCases: BookFavoritesUseCases

): ViewModel() {

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    private var currentPage = 1
    private var isLoading = false

    private val limit: Int = 7
    private val startCategory: BookCategories = BookCategories.All
    private var categories: HashMap<BookCategories, Int>? = null

    init {
        viewModelScope.launch {
            categories = bookListUseCases.getCategories()
        }
    }

    fun addBookToFavorites(book: Book): Result<Unit> {
        return bookFavoritesUseCases.addToFavorites(book)
    }

    fun deleteBookToFavorites(book: Book): Result<Unit> {
        return bookFavoritesUseCases.deleteFromFavorites(book)
    }

    private fun getBooksFromServer(bookCategories: BookCategories, limit:Int?, page:Int?){
        viewModelScope.launch {
            categories?.get(bookCategories)?.let {
                val data = bookListUseCases.getBooksList(it, limit, page)
                _books.postValue(data)
                Log.d("Books", "getBooksFromServer: Normal, category: $bookCategories")
                return@launch
            }
            val data = when(bookCategories){
                BookCategories.All -> getAllBooksList(limit, page)
                BookCategories.Favorites -> getFavoritesList()
                else -> emptyList()
            }
            Log.d("Books", "getBooksFromServer: Error, category: $bookCategories")
            _books.postValue(data)
        }
    }

    private fun getFavoritesList(): List<Book>{
        return emptyList()
    }

    private suspend fun getAllBooksList(limit:Int?, page:Int?): List<Book>{
        return bookListUseCases.getAllBooks(limit, page)
    }

    fun getBooksFromServer(bookCategories: BookCategories){
        getBooksFromServer(bookCategories, null,null)
    }

    fun initBooksFromServer(){
        getBooksFromServer(startCategory, limit, 1)
    }

    fun saveThemePreference(context: Context, isNightMode: Boolean) {
        val sharedPreferences = context.getSharedPreferences("AppSettings", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("NightMode", isNightMode)
        editor.apply()
    }

    fun loadThemePreference(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences("AppSettings", MODE_PRIVATE)
        return sharedPreferences.getBoolean("NightMode", false)
    }

    fun saveBookDisplayStyle(context: Context, displayStyle: BookView.BookDisplayStyle) {
        val sharedPreferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("BookDisplayStyle", displayStyle.name)
        editor.apply()
    }

    fun loadBookDisplayStyle(context: Context): BookView.BookDisplayStyle {
        val sharedPreferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val savedStyle = sharedPreferences.getString("BookDisplayStyle", BookView.BookDisplayStyle.WIDE.name)
        return BookView.BookDisplayStyle.valueOf(savedStyle ?: BookView.BookDisplayStyle.WIDE.name)
    }

    fun loadNextPage(bookCategories: BookCategories) {
        if (isLoading) return

        isLoading = true
        viewModelScope.launch {
            val nextPage = currentPage + 1
            categories?.get(bookCategories)?.let { categoryId ->
                val data = bookListUseCases.getBooksList(categoryId, limit, nextPage)
                _books.postValue((_books.value ?: emptyList()) + data)
            } ?: run {
                val data = getAllBooksList(limit, nextPage)
                _books.postValue((_books.value ?: emptyList()) + data)
            }
            currentPage++
            isLoading = false
        }
    }


    // TODO Saving scroll position after collapse app

    // TODO Show scroll position
}