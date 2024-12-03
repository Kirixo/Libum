package com.project.libum.presentation.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.project.libum.R
import com.project.libum.data.dto.Book
import com.project.libum.databinding.ActivityBookReaderBinding
import com.project.libum.presentation.viewmodel.BookReadActivityModel

class BookReaderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookReaderBinding
    private val bookReaderViewModel: BookReadActivityModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeActivity()

        binding.contentButton.setOnClickListener{ view ->
            changeContentVisibility()
        }

        binding.viewBookContent.hideContentButton.setOnClickListener {
            changeContentVisibility()
        }

        binding.viewBookContent.backButton.setOnClickListener{
            finish()
        }

        val bookData = intent.getParcelableExtra<Book>(BOOK_DATA)
        bookData?.let {
            bookReaderViewModel.setBookData(it)
        } ?: Log.e("BookReaderActivity", "BOOK_DATA is null")
    }

    private fun changeContentVisibility(){
        if (binding.viewBookContent.main.visibility == View.VISIBLE) {
            binding.viewBookContent.main.visibility = View.GONE
            binding.contentButton.visibility = View.VISIBLE
        } else {
            binding.viewBookContent.main.visibility = View.VISIBLE
            binding.contentButton.visibility = View.GONE
        }
        Log.d("BookReaderActivity", "onCreate: ${binding.viewBookContent.main.visibility }")
    }

    private fun initializeActivity(){
        binding = ActivityBookReaderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        const val BOOK_DATA = "BOOK_DATA"
    }
}