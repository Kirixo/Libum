package com.project.libum.domain.usecase

import android.util.Log
import com.project.libum.data.dto.BookMethaData
import com.project.libum.data.local.dao.BookEntity
import com.project.libum.domain.repository.StoredBookRepository
import java.nio.charset.Charset

class BookUseCases(
    private val storedBookRepository: StoredBookRepository
) {

    private suspend fun fetchBook(bookId: Int, callback: (String?) -> Unit ){
        try {
            storedBookRepository.fetchBook(bookId){ _, encoding ->
                val bookData = storedBookRepository.readBookContent(
                    bookId.toString(),
                    encoding?:Charset.defaultCharset()
                )
                Log.d("ABOBA", "fetchBook fetch: ${bookData?.length}")
                callback(bookData)
            }
        }catch (e: Exception){
            Log.d("ABOBA", "fetchBook error: $e")
            callback(null)
        }
    }

    suspend fun getBookContent(bookId: Int, callback: (String?) -> Unit){
        if(storedBookRepository.isExist(bookId.toString())){
            val bookContent = storedBookRepository.readBookContent(
                bookId.toString(),
                Charset.forName(storedBookRepository.getBookMetaData(bookId)!!.encoding))
            callback(bookContent)
        }else{
            fetchBook(bookId, callback)
        }
    }

    suspend fun updateReadPercent(bookId: Int, percent: Int){
        storedBookRepository.updateReadPercent(bookId, percent)
    }

    suspend fun updateLastReadPage(bookId: Int, page: Int){
        storedBookRepository.updateLastReadPage(bookId, page)
    }
}