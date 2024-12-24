package com.project.libum.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.libum.data.dto.Book

class BookReadActivityModel: ViewModel() {

    private var _bookData = MutableLiveData<Book>()
    var bookData: LiveData<Book> =_bookData

    fun setBookData(book: Book){
        _bookData.postValue(book)
    }

    fun getReadPercent(): Int{
        return 20
    }

}