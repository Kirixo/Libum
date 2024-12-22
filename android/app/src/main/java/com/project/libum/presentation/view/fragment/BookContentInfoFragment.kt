package com.project.libum.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.project.libum.data.dto.Book
import com.project.libum.databinding.FragmentBookContenInfoBinding
import com.project.libum.presentation.viewmodel.BookReadActivityModel

class BookContentInfoFragment : Fragment() {

    private val bookReaderViewModel: BookReadActivityModel by activityViewModels<BookReadActivityModel>()
    private lateinit var binding: FragmentBookContenInfoBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookReaderViewModel.bookData.observe(viewLifecycleOwner){ book ->
            setStartUpState(book)
        }

        bookReaderViewModel.currentPage.observe(viewLifecycleOwner){
            onUpdate()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookContenInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun setStartUpState(book: Book){
        binding.bookTitleText.text = book.title
        binding.authorName.text = book.author
        binding.readProgressBar.progress = bookReaderViewModel.getReadPercent()
        binding.readProgressPercent.text ="${bookReaderViewModel.getReadPercent()}%"
    }

    private fun onUpdate(){
        binding.readProgressBar.progress = bookReaderViewModel.getReadPercent()
        binding.readProgressPercent.text ="${bookReaderViewModel.getReadPercent()}%"
    }
}