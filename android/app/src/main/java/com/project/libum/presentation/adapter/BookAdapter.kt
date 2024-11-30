package com.project.libum.presentation.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.libum.R
import com.project.libum.data.dto.Book
import com.project.libum.databinding.ViewBookSlimBinding
import com.project.libum.databinding.ViewBookWideBinding
import com.project.libum.presentation.view.custom.BookView

class BookAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var books = listOf<Book>()
    private var displayStyle = BookView.BookDisplayStyle.WIDE
    private var onFavoriteClick: ((Book) -> Unit)? = null

    fun setOnFavoriteClickListener(listener: (Book) -> Unit) {
        onFavoriteClick = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setBooks(newBooks: List<Book>? ) {
        books = newBooks ?: emptyList()
        notifyDataSetChanged()
        Log.d("Books", "setStyle: size set: ${books.size} ")
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setStyle(displayStyle: BookView.BookDisplayStyle){
        this.displayStyle = displayStyle
        notifyDataSetChanged()
        Log.d("Books", "setStyle: style set: ${displayStyle.toString()} ")
    }

    override fun getItemViewType(position: Int): Int {
        return when (displayStyle) {
            BookView.BookDisplayStyle.WIDE -> BookView.BookDisplayStyle.WIDE.id
            BookView.BookDisplayStyle.SLIM -> BookView.BookDisplayStyle.SLIM.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("Books", "onCreateViewHolder: book changed/init")
        return when (viewType) {
            BookView.BookDisplayStyle.WIDE.id-> {
                val binding = ViewBookWideBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                BookViewHolderWide(binding, )
            }
            BookView.BookDisplayStyle.SLIM.id-> {
                val binding = ViewBookSlimBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                BookViewHolderSlim(binding)
            }
            else -> throw IllegalArgumentException("Unknown book view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val book = books[position]
        if (holder is BookViewHolder) {
            holder.bind(book)
            holder.setFavoriteClickListener {
                onFavoriteClick?.invoke(book)
            }
            holder.updateFavoriteState(book.isFavorite)
        }
    }


    override fun getItemCount(): Int = books.size

    class BookViewHolderWide(private val binding: ViewBookWideBinding) :
        RecyclerView.ViewHolder(binding.root),
        BookViewHolder {

        override fun bind(book: Book) {
            binding.bookTitleText.text = book.title
            binding.authorText.text = book.author
        }

        override fun setFavoriteClickListener(action: () -> Unit) {
            binding.favoriteButton.setOnClickListener { action() }
        }

        override fun updateFavoriteState(isFavorite: Boolean) {
            binding.favoriteButton.isActivated = isFavorite
        }
    }

    class BookViewHolderSlim(private val binding: ViewBookSlimBinding) :
        RecyclerView.ViewHolder(binding.root),
        BookViewHolder  {

        override fun bind(book: Book) {
            binding.bookTitleText.text = book.title
        }

        override fun setFavoriteClickListener(action: () -> Unit) {
            binding.favoriteButton.setOnClickListener { action() }
        }

        override fun updateFavoriteState(isFavorite: Boolean) {
            binding.favoriteButton.isActivated = isFavorite
        }

    }
}
