package com.project.libum.presentation.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.libum.R
import com.project.libum.presentation.viewmodel.HomeViewModel
import com.project.libum.databinding.FragmentHomeBinding
import com.project.libum.presentation.adapter.BookAdapter
import com.project.libum.presentation.adapter.SpacingItemDecoration
import com.project.libum.presentation.view.custom.BookView
import com.project.libum.presentation.viewmodel.MainActivityModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val mainActivityModel: MainActivityModel by activityViewModels<MainActivityModel>()
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var bookAdapter: BookAdapter
    private var bookStyle: BookView.BookDisplayStyle = BookView.BookDisplayStyle.WIDE
    private var isActivatedChangeButton = false

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

        if (savedInstanceState != null) {
            isActivatedChangeButton = savedInstanceState.getBoolean(CHANGE_BUTTON_STYLE, false)

            bookStyle = when(savedInstanceState.getInt(BOOK_STYLE, 0)){
                0 -> BookView.BookDisplayStyle.WIDE
                1 -> BookView.BookDisplayStyle.SLIM
                else -> BookView.BookDisplayStyle.WIDE
            }

            binding.actionField.listStyleChangerButton.isActivated = isActivatedChangeButton
        }

        binding.actionField.listStyleChangerButton.setOnClickListener{ button ->
            button.isActivated = !button.isActivated
            bookStyle = if(!button.isActivated){
                BookView.BookDisplayStyle.WIDE
            }else{
                BookView.BookDisplayStyle.SLIM
            }
            setBookStyle()
            isActivatedChangeButton = button.isActivated
        }

        mainActivityModel.books.observe(viewLifecycleOwner) { books ->
            bookAdapter.setBooks(books)
            bookAdapter.setStyle(bookStyle)
        }

        initializeBookAdapter()

        bookAdapter.setBooks(mainActivityModel.books.value)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(BOOK_STYLE, bookStyle.id)
        outState.putBoolean(CHANGE_BUTTON_STYLE, isActivatedChangeButton)
        super.onSaveInstanceState(outState)
    }


    private fun initializeBookAdapter(){
        bookAdapter = BookAdapter()
        val spacingDecoration = SpacingItemDecoration(resources.getDimensionPixelSize(R.dimen.item_spacing))
        binding.bookList.addItemDecoration(spacingDecoration)
        binding.bookList.adapter = bookAdapter
        setBookStyle()
    }

    private fun setBookStyle(){
        when(bookStyle){
            BookView.BookDisplayStyle.WIDE -> setWideBookAdapter()
            BookView.BookDisplayStyle.SLIM -> setSlimBookAdapter()
        }
    }

    private fun setWideBookAdapter(){
        Log.d("Books", "setWideBookAdapter: Wide set")
        binding.bookList.layoutManager = LinearLayoutManager(context)
        bookAdapter.setStyle(bookStyle)
    }

    private fun setSlimBookAdapter(){
        Log.d("Books", "setSlimBookAdapter: Slim set")
        val gridLayoutManager = GridLayoutManager(context, 3)
        binding.bookList.layoutManager = gridLayoutManager
        bookAdapter.setStyle(bookStyle)
    }


    companion object{
        const val BOOK_STYLE: String = "BOOK_STYLE"
        const val CHANGE_BUTTON_STYLE: String = "CHANGE_BUTTON_STYLE"
    }
}