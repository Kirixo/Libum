package com.project.libum.data.local

import android.content.Context
import android.net.Uri
import com.ibm.icu.text.CharsetDetector
import core.reader.html.Parser
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream
import java.nio.charset.Charset

class FileStorageController(private val context: Context) {

    val cacheDir: File = context.cacheDir

    fun saveBookFile(fileName: String, content: String) {
        val file = File(cacheDir, fileName)
        FileOutputStream(file).use {
            it.write(content.toByteArray())
        }
    }

    private fun getBookUriByName(fileName: String): Uri {
        val file = File(cacheDir, fileName)
        return if (file.exists()) {
            Uri.fromFile(file)
        } else {
            throw FileNotFoundException("File with name $fileName not found in cache directory")
        }
    }

    fun getBookContent(fileName: String): String? {
        return try {
            context.contentResolver.openInputStream(getBookUriByName(fileName))?.use { inputStream ->
                inputStream.bufferedReader().use { it.readText() }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getBookContent(fileName: String, encoding: Charset): String? {
        return try {
            context.contentResolver.openInputStream(getBookUriByName(fileName))?.use { inputStream ->
                inputStream.bufferedReader(encoding).use { it.readText() }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun deleteBookFile(fileName: String): Boolean {
        val file = File(cacheDir, fileName)
        return file.exists() && file.delete()
    }

    fun isExistBook(fileName: String): Boolean {
        val file = File(cacheDir, fileName)
        return file.exists()
    }

    fun getAllBooks(): List<String> {
        val files = cacheDir.listFiles()
        val books = mutableListOf<String>()

        files?.forEach { file ->
            if (file.isFile) {
                books.add(file.name)
            }
        }
        return books
    }

    fun saveBookFileFromStream(inputStream: InputStream, destinationFile: File) {
        val buffer = inputStream.readBytes()
        FileOutputStream(destinationFile).use {
            it.write(buffer)
        }
    }


}
