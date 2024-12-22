package com.project.libum.domain.repository

import android.util.Log
import com.project.libum.data.local.FileStorageController
import com.project.libum.data.local.dao.BookDao
import com.project.libum.data.local.dao.BookEntity
import com.project.libum.data.remote.ApiClient.apiService
import core.reader.html.Parser
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.Charset


class StoredBookRepository(
    private val fileStorageController: FileStorageController,
    private val bookDao: BookDao
) {

    fun readBookContent(bookId: String, encoding: Charset): String?{
        return fileStorageController.getBookContent("${bookId}.fb2", encoding)
    }

    fun isExist(bookId: String): Boolean{
        return fileStorageController.isExistBook("${bookId}.fb2")
    }

    suspend fun fetchBook(
        bookId: Int,
        bookFetchedCallback: (File, Charset?) -> Unit
    ) {
        val response = apiService.getBookFile(bookId)
        Log.d("StoredBookRepository", "fetchBook response: ${response.code()}")

        if (response.isSuccessful) {
            response.body()?.let { responseBody ->
                val inputStream = responseBody.byteStream()

                val bufferedStream = inputStream.buffered()
                val peekedBytes = ByteArray(1024)
                val bytesRead = bufferedStream.read(peekedBytes)

                val detectedEncoding = Parser.getEncoding(peekedBytes, bytesRead)
                val encoding = detectedEncoding?:Charset.defaultCharset()
                val destinationFile = File(fileStorageController.cacheDir, "$bookId.fb2")

                FileOutputStream(destinationFile).use { output ->
                    output.write(peekedBytes, 0, bytesRead)
                    bufferedStream.copyTo(output)
                }

                saveBookMetaData(BookEntity(
                    bookId,
                    encoding.name(),
                    0))

                bookFetchedCallback(destinationFile, detectedEncoding)
            }
        } else {
            throw Exception("Failed to download file: ${response.code()} ${response.message()}")
        }
    }

    private suspend fun saveBookMetaData(bookEntity: BookEntity){
        bookDao.insertBook(bookEntity)
    }

    suspend fun updateReadPercent(bookId: Int, percent: Int){
        if (percent in 0..100){
            bookDao.updateReadPercent(bookId, percent)
        }else{
            throw IllegalArgumentException()
        }
    }

    suspend fun updateLastReadPage(bookId: Int, page: Int){
        bookDao.updateLastReadPage(bookId, page)
    }

    suspend fun getBookMetaData(bookId: Int): BookEntity?{
        return bookDao.getBook(bookId)
    }

}