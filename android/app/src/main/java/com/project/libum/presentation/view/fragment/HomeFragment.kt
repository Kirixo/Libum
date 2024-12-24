package com.project.libum.presentation.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.libum.R
import com.project.libum.core.utils.SwipeGestureListener
import com.project.libum.data.dto.Book
import com.project.libum.databinding.FragmentHomeBinding
import com.project.libum.presentation.adapter.BookAdapter
import com.project.libum.presentation.adapter.GridSpacingItemDecoration
import com.project.libum.presentation.adapter.SpacingItemDecoration
import com.project.libum.presentation.view.activity.BookReaderActivity
import com.project.libum.presentation.view.activity.BookReaderActivity.Companion.BOOK_DATA
import com.project.libum.presentation.view.custom.BookView
import com.project.libum.presentation.view.extension.showErrorMessage
import com.project.libum.presentation.viewmodel.HomeViewModel
import com.project.libum.presentation.viewmodel.MainActivityModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.max

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val mainActivityModel: MainActivityModel by activityViewModels<MainActivityModel>()
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var bookAdapter: BookAdapter

    private lateinit var gestureDetector: GestureDetector

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookStyle = mainActivityModel.loadBookDisplayStyle(requireContext())
        homeViewModel.changeBookStyle(bookStyle)

        binding.actionField.listStyleChangerButton.setOnClickListener{
            changeBookStyleNext()
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
            mainActivityModel.saveBookDisplayStyle(requireContext(), displayStyle)
            changeStateOfBookStyleButton()
        }

        homeViewModel.catalogState.observe(viewLifecycleOwner){
            val (previous, current, next) = homeViewModel.getSurroundingCatalogStates()

            binding.bookCategories.previousCatalogState.text = previous.name
            binding.bookCategories.currentCatalogState.text = current.name
            binding.bookCategories.nextCatalogState.text = next.name

            mainActivityModel.getBooksFromServer(it)
        }

        binding.bookCategories.nextCatalogState.setOnClickListener{
            homeViewModel.changeNextCatalogState()
        }

        binding.bookCategories.previousCatalogState.setOnClickListener{
            homeViewModel.changePreviousCatalogState()
        }

        gestureDetector = GestureDetector(context, SwipeGestureListener(
            onSwipeRight = {
                homeViewModel.changeNextCatalogState()
                Log.d("HomeFragment", "onViewCreated: swipe right")
                           },
            onSwipeLeft = {
                homeViewModel.changePreviousCatalogState()
                Log.d("HomeFragment", "onViewCreated: swipe left")
            }
            )
        )

        binding.catalogBackground.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
        }

        initializeBookAdapter()
        setupRecyclerViewPagination()
    }

    override fun onResume() {
        super.onResume()
        mainActivityModel.initBooksFromServer()
    }

    private fun initializeBookAdapter() {
        bookAdapter = BookAdapter()

        bookAdapter.setOnFavoriteClickListener { book, isFavorite ->
            lifecycleScope.launch {
                val result = if (isFavorite) {
                    mainActivityModel.addBookToFavorites(book)
                } else {
                    mainActivityModel.deleteBookToFavorites(book)
                }
                result.onSuccess {
                    bookAdapter.notifyItemChanged(mainActivityModel.books.value!!.indexOf(book))
                }.onFailure {
                    showErrorMessage(context, "Failed to update favorite status for ${book.title}")
                }
            }
        }

        bookAdapter.setOnBookClickView { book ->
            openBookReaderActivity(book)
        }

        binding.bookList.adapter = bookAdapter
    }

    private fun setupRecyclerViewPagination() {
        binding.bookList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as? LinearLayoutManager
                    ?: return

                val totalItemCount = layoutManager.itemCount

                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                val visibleThreshold = 5

                if (totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    loadMoreBooks()
                }
            }
        })
    }

    private fun openBookReaderActivity(book: Book){
        val intent = Intent(context, BookReaderActivity::class.java)
        intent.putExtra(BOOK_DATA, book)
        startActivity(intent)
    }

    private fun changeBookStyleNext(){
        val bookStyle = if ( homeViewModel.bookStyle.value == BookView.BookDisplayStyle.WIDE) {
            BookView.BookDisplayStyle.SLIM
        } else {
            BookView.BookDisplayStyle.WIDE
        }

        homeViewModel.changeBookStyle(bookStyle)
    }

    private fun changeStateOfBookStyleButton(){
        when(homeViewModel.bookStyle.value){
            BookView.BookDisplayStyle.WIDE -> setStyleButtonWideIcon()
            BookView.BookDisplayStyle.SLIM ->  setStyleButtonSlimIcon()
            null -> setStyleButtonWideIcon()
        }
    }

    private fun setStyleButtonWideIcon(){
        val drawable = ResourcesCompat.getDrawable(
            requireContext().resources,
            R.drawable.ic_standart_list_24dp,
            requireContext().theme
        )

        binding.actionField.listStyleChangerButton.setImageDrawable(drawable)
    }

    private fun setStyleButtonSlimIcon(){
        val drawable = ResourcesCompat.getDrawable(
            requireContext().resources,
            R.drawable.ic_block_list_24dp,
            requireContext().theme
        )

        binding.actionField.listStyleChangerButton.setImageDrawable(drawable)
    }

    private fun setWideBookAdapter(){
        clearItemDecorations(binding.bookList)
        binding.bookList.layoutManager = LinearLayoutManager(context)

        val spacingDecoration = SpacingItemDecoration(
            resources.getDimensionPixelSize(R.dimen.item_spacing),
            resources.getDimensionPixelSize(R.dimen.first_item_spacing),
            resources.getDimensionPixelSize(R.dimen.first_item_spacing)
        )

        binding.bookList.addItemDecoration(spacingDecoration)
    }

    private fun setSlimBookAdapter() {
        clearItemDecorations(binding.bookList)
        val gridLayoutManager = GridLayoutManager(context, getSpanCount())
        binding.bookList.layoutManager = gridLayoutManager

        val spacingDecoration = GridSpacingItemDecoration(
            resources.getDimensionPixelSize(R.dimen.item_spacing),
            resources.getDimensionPixelSize(R.dimen.first_item_spacing),
            resources.getDimensionPixelSize(R.dimen.first_item_spacing)
        )
        binding.bookList.addItemDecoration(spacingDecoration)
    }

    private fun getSpanCount(): Int{
        val totalWidth = resources.displayMetrics.widthPixels
        val itemWidth = resources.getDimensionPixelSize(R.dimen.slim_book_width)
         return  max(1, totalWidth / itemWidth)
    }

    private fun clearItemDecorations(recyclerView: RecyclerView) {
        while (recyclerView.itemDecorationCount > 0) {
            recyclerView.removeItemDecorationAt(0)
        }
    }

    private fun loadMoreBooks() {
        mainActivityModel.loadNextPage(homeViewModel.catalogState.value ?: HomeViewModel.BookCategories.All)
    }

}