package com.project.libum.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.libum.presentation.view.custom.BookView
import com.project.libum.presentation.view.custom.BookView.BookDisplayStyle

class HomeViewModel : ViewModel() {

    private val _bookStyle = MutableLiveData<BookDisplayStyle>()
    val bookStyle: LiveData<BookDisplayStyle> = _bookStyle

    init {
        changeBookStyle(STANDARD_BOOK_STYLE)
    }

    private fun changeBookStyle(bookStyle: BookDisplayStyle){
        _bookStyle.postValue(bookStyle)
    }

    fun changeBookStyleByActivated(isActivated: Boolean){
        val bookStyle = if(!isActivated) BookDisplayStyle.SLIM else BookDisplayStyle.WIDE
        changeBookStyle(bookStyle)
    }

    companion object{

        val STANDARD_BOOK_STYLE: BookDisplayStyle = BookDisplayStyle.WIDE
    }
}