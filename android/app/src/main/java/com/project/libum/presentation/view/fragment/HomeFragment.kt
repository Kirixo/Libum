package com.project.libum.presentation.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.libum.R
import com.project.libum.presentation.viewmodel.HomeViewModel
import com.project.libum.databinding.FragmentHomeBinding
import com.project.libum.presentation.adapter.BookAdapter
import com.project.libum.presentation.adapter.SpacingItemDecoration
import com.project.libum.presentation.view.custom.BookView
import com.project.libum.presentation.viewmodel.MainActivityModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val mainActivityModel: MainActivityModel by activityViewModels<MainActivityModel>()
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var bookAdapter: BookAdapter
    private var isActivatedBookStyleButton = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.actionField.listStyleChangerButton.setOnClickListener{
            changeStateOfBookStyleButton()
            homeViewModel.changeBookStyleByActivated(isActivatedBookStyleButton)
        }

        mainActivityModel.books.observe(viewLifecycleOwner) { books ->
            bookAdapter.setBooks(books)
        }

        homeViewModel.bookStyle.observe(viewLifecycleOwner){ displayStyle ->
            when(displayStyle){
                BookView.BookDisplayStyle.SLIM -> setSlimBookAdapter()
                BookView.BookDisplayStyle.WIDE -> setWideBookAdapter()
                else -> setWideBookAdapter()
            }
            bookAdapter.setStyle(displayStyle)
        }

        initializeBookAdapter()
    }


    private fun initializeBookAdapter(){
        bookAdapter = BookAdapter()
        val spacingDecoration = SpacingItemDecoration(resources.getDimensionPixelSize(R.dimen.item_spacing))
        binding.bookList.addItemDecoration(spacingDecoration)

        bookAdapter.setOnFavoriteClickListener { book ->
            if (book.isFavorite) {
                mainActivityModel.deleteBookToFavorites(book)
            } else {
                mainActivityModel.addBookToFavorites(book)
            }
        }

        binding.bookList.adapter = bookAdapter
    }

    private fun changeStateOfBookStyleButton(){
        val button = binding.actionField.listStyleChangerButton
        button.isActivated = !button.isActivated
        isActivatedBookStyleButton = button.isActivated
    }

    private fun setWideBookAdapter(){
        binding.bookList.layoutManager = LinearLayoutManager(context)
    }

    private fun setSlimBookAdapter(){
        val gridLayoutManager = GridLayoutManager(context, SLIM_BOOK_IN_ROW_COUNT)
        binding.bookList.layoutManager = gridLayoutManager
    }

    companion object{
        const val SLIM_BOOK_IN_ROW_COUNT = 3
    }
}