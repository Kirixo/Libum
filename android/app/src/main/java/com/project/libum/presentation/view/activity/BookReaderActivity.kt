package com.project.libum.presentation.view.activity

import android.graphics.Color
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
import com.project.libum.presentation.view.fragment.BookContentInfoFragment
import com.project.libum.presentation.viewmodel.BookReadActivityModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookReaderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookReaderBinding
    private val bookReaderViewModel: BookReadActivityModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeActivity()

        binding.bookText.setBackgroundColor(Color.TRANSPARENT)

        bookReaderViewModel.initBookReader(this@BookReaderActivity, binding.bookText){
            val bookData = intent.getParcelableExtra<Book>(BOOK_DATA)
            bookData?.let {
                bookReaderViewModel.setBookData(it)
            } ?: Log.e("BookReaderActivity", "BOOK_DATA is null")
        }

        binding.contentButton.setOnClickListener{
            changeContentVisibility()
        }

        binding.viewBookContent.hideContentButton.setOnClickListener {
            changeContentVisibility()
        }

        binding.viewBookContent.backButton.setOnClickListener{
            finish()
        }

        binding.nextPage.setOnClickListener {
            loadNextPage()
        }

        binding.prevPage.setOnClickListener {
            loadPrevPage()
        }

        bookReaderViewModel.bookText.observe(this){

            val lastReadPage = bookReaderViewModel.bookData.value?.lastReadPage
            if (lastReadPage != null && lastReadPage > 0){
                bookReaderViewModel.goPage(lastReadPage)
            }else{
                loadNextPage()
            }

            if (it.isNotEmpty()) {
                binding.loaderBar.visibility = View.GONE
            }

            binding.maxPages.text = bookReaderViewModel.maxPagesNum().toString()
        }

        bookReaderViewModel.bookData.observe(this){ bookData ->
            bookReaderViewModel.getBookText(bookData.id)
        }

        bookReaderViewModel.currentPage.observe(this) { bookPage ->
            binding.bookText.loadDataWithBaseURL(
                null,
                bookPage,
                "text/html",
                bookReaderViewModel.getBookEncoding(),
                null
            )
            binding.currentPage.text = bookReaderViewModel.currentPageNum().toString()
        }
    }


    private fun loadNextPage(){
        bookReaderViewModel.goNextPageHtml()
    }
    private fun loadPrevPage(){
        bookReaderViewModel.goPrevPageHtml()
    }

    private fun changeContentVisibility(){
        when (binding.viewBookContent.main.visibility) {
            View.VISIBLE -> {
                showBookContent()
            }
            else -> {
                hideBookContent()
            }
        }
        Log.d("BookReaderActivity", "onCreate: ${binding.viewBookContent.main.visibility }")
    }

    private fun showBookContent(){
        binding.viewBookContent.main.visibility = View.GONE
        binding.controlButtons.visibility = View.VISIBLE
        binding.informationLayout.visibility = View.VISIBLE
    }

    private fun hideBookContent(){
        binding.viewBookContent.main.visibility = View.VISIBLE
        binding.controlButtons.visibility = View.GONE
        binding.informationLayout.visibility = View.INVISIBLE
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

    override fun onPause() {
        super.onPause()
        if (bookReaderViewModel.currentPageNum() > 0){
            bookReaderViewModel.saveMethaData()
        }
    }

    companion object {
        const val BOOK_DATA = "BOOK_DATA"
    }
}