package com.project.libum.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.libum.presentation.view.custom.BookView.BookDisplayStyle

class HomeViewModel : ViewModel() {

    private val _bookStyle = MutableLiveData<BookDisplayStyle>()
    val bookStyle: LiveData<BookDisplayStyle> = _bookStyle


    private val _catalogState = MutableLiveData(BookCategories.All)
    val catalogState: LiveData<BookCategories> = _catalogState

    private val categories = BookCategories.entries
    private var currentCategoryIndex = 0

    init {
        changeBookStyle(STANDARD_BOOK_STYLE)
    }

    private fun changeBookStyle(bookStyle: BookDisplayStyle){
        _bookStyle.postValue(bookStyle)
    }

    fun changeBookStyleByActivated(isActivated: Boolean){
        val bookStyle = if(isActivated) BookDisplayStyle.SLIM else BookDisplayStyle.WIDE
        changeBookStyle(bookStyle)
    }

    fun getSurroundingCatalogStates(): Triple<BookCategories, BookCategories, BookCategories> {
        val current = categories[currentCategoryIndex]
        val previous = categories[if (currentCategoryIndex - 1 < 0) categories.size - 1 else currentCategoryIndex - 1]

        val next = categories[(currentCategoryIndex + 1) % categories.size]
        return Triple(previous, current, next)
    }

    fun changeNextCatalogState() {
        currentCategoryIndex = (currentCategoryIndex + 1) % categories.size
        changeCatalogState(categories[currentCategoryIndex])
    }

    fun changePreviousCatalogState() {
        currentCategoryIndex = if (currentCategoryIndex - 1 < 0) {
            categories.size - 1
        } else {
            currentCategoryIndex - 1
        }
        changeCatalogState(categories[currentCategoryIndex])
    }

    private fun changeCatalogState(bookCategory: BookCategories){
        _catalogState.postValue(bookCategory)
    }

    enum class BookCategories{
        MyBooks,
        All,
        Reading,
        Favorites,
        Complete,
        Archive,
    }

    companion object{

        val STANDARD_BOOK_STYLE: BookDisplayStyle = BookDisplayStyle.WIDE
    }
}