package com.project.libum.presentation.viewmodel

import android.content.Context
import android.util.Log
import android.view.ViewTreeObserver
import android.webkit.WebView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.libum.data.dto.Book
import com.project.libum.domain.usecase.BookUseCases
import core.reader.LBReader
import core.reader.TextUtils
import core.reader.html.BookHtml
import core.reader.html.Parser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class BookReadActivityModel @Inject constructor(
    private val bookUseCases: BookUseCases
) : ViewModel() {

    private var _bookData = MutableLiveData<Book>()
    var bookData: LiveData<Book> =_bookData

    private var lbReader: LBReader? = null

    private var _bookText = MutableLiveData<String>("")
    val bookText: LiveData<String> = _bookText

    private val _currentPage = MutableLiveData<String>("")
    val currentPage: LiveData<String>  = _currentPage

    private var _readerTheme = MutableLiveData<BookHtml.BookStyle>()
    var readerTheme: LiveData<BookHtml.BookStyle> = _readerTheme

    fun initBookReader(context: Context, webView: WebView, callback: ()->Unit) {
        webView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (webView.width > 0 && webView.height > 0) {
                    webView.viewTreeObserver.removeOnGlobalLayoutListener(this)

                    val widthDp = TextUtils.pxToDp(context, webView.width.toFloat())
                    val heightDp = TextUtils.pxToDp(context, webView.height.toFloat())

                    lbReader = LBReader(widthDp.toInt(), heightDp.toInt()-10, context)
                    callback()
                }
            }
        })
    }

    fun getBookText(bookId: Int){
        viewModelScope.launch {
            bookUseCases.getBookContent(bookId){ text ->
                if (text == null) Log.d("ABOBA", "getBookText: Book with id $bookId text is null")
                else setBootText(text)
            }
        }
    }

    private fun setBootText(text: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                lbReader?.setBookText(text, getBookEncoding())
                lbReader?.setTextColor(ColorThemes.DARK.hexColorTheme.text)
                lbReader?.setBackgroundColor(ColorThemes.DARK.hexColorTheme.background)
            }
            _bookText.postValue(text)
        }
    }

    fun goNextPageHtml(){
        goPage(currentPageNum()+1)
    }

    fun goPrevPageHtml(){
        goPage(currentPageNum()-1)
    }

    fun goPage(pageNumber: Int){
        Log.d("ABOBA", "goPage: $pageNumber")
        if (validatePageNumber(pageNumber)){
            _currentPage.postValue(lbReader?.getPageHtml(pageNumber))
        }
    }

    fun getBookEncoding(): String{
        return Parser.getEncoding(bookText.value!!)
    }

    private fun validatePageNumber(nextPageNum: Int):Boolean{
        return nextPageNum > 0 && nextPageNum <= maxPagesNum()
    }

    fun setBookData(book: Book){
        _bookData.postValue(book)
    }

    fun maxPagesNum(): Int = lbReader?.totalBookPages ?: 0

    fun currentPageNum(): Int = lbReader?.currentBookPage ?: 0

    fun getReadPercent(): Int {
        val totalPagesCount = maxPagesNum()
        val currentPagesCount = currentPageNum()
        if (totalPagesCount == 0 || currentPagesCount == 0) return 0
        return (currentPageNum() * 100) / totalPagesCount
    }

    fun saveMethaData(){
        viewModelScope.launch {
            bookUseCases.updateReadPercent(bookData.value!!.id, getReadPercent())
            bookUseCases.updateLastReadPage(bookData.value!!.id, currentPageNum())
        }
    }
}

enum class ColorThemes {
    STANDARD {
        override val hexColorTheme: HexColorTheme
            get() = HexColorTheme("#F7F7F7", "#0E0E0E")
    },

    DARK {
        override val hexColorTheme: HexColorTheme
            get() = HexColorTheme("#0E0E0E", "#F7F7F7")
    };

    abstract val hexColorTheme: HexColorTheme
}

data class HexColorTheme(
    val background: String,
    val text: String
)

