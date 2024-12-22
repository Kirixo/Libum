package core.reader

import android.content.Context
import core.reader.html.BookHtml

class LBReader(
    private val pageWidth: Int,
    private val pageHeight: Int,
    private val context: Context
) {
    private lateinit var bookHtml: BookHtml
    var totalBookPages = 0
        private set
    var currentBookPage = 0
        private set

    fun setBookText(htmlBookText: String, encoding: String){
        val bookHtml = BookHtml(htmlBookText, pageWidth, pageHeight, encoding, context)
        setBookHtml(bookHtml)
    }

    private fun setBookHtml(bookHtml: BookHtml){
        this.bookHtml = bookHtml
        totalBookPages = bookHtml.getTotalPages()
    }

    fun getNextPageHtml(): String{
        return getPageHtml(++this.currentBookPage)
    }

    fun getPreviousPageHtml(): String{
        return getPageHtml(--this.currentBookPage)
    }

    fun setTextColor(color: String){
        val style = bookHtml.currentStyle
        style.textColorHEX = color
        bookHtml.currentStyle = style
    }

    fun setBackgroundColor(color: String){
        val style = bookHtml.currentStyle
        style.backgroundColorHEX = color
        bookHtml.currentStyle = style
    }

    fun setStyle(bookStyle: BookHtml.BookStyle){
        bookHtml.currentStyle = bookStyle
        totalBookPages = bookHtml.getTotalPages()
    }

    fun getPageHtml(pageNum: Int): String{
        this.currentBookPage = pageNum
        return bookHtml.getPage(pageNum)
    }


}