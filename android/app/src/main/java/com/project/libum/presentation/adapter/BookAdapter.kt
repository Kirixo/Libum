package com.project.libum.presentation.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.libum.data.dto.Book
import com.project.libum.databinding.ViewBookSlimBinding
import com.project.libum.databinding.ViewBookWideBinding
import com.project.libum.presentation.view.custom.BookView
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.project.libum.R

class BookAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var books = listOf<Book>()
    private var displayStyle = BookView.BookDisplayStyle.WIDE
    private var onFavoriteClick: ((Book, Boolean) -> Unit)? = null
    private var onBookClick: ((Book) -> Unit)? = null

    fun setOnFavoriteClickListener(listener: (Book, Boolean) -> Unit) {
        onFavoriteClick = listener
    }

    fun setOnBookClickView(listener: (Book) -> Unit){
        onBookClick = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setBooks(newBooks: List<Book>) {
        val diffResult = DiffUtil.calculateDiff(BookDiffCallback(books, newBooks))
        books = newBooks
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        if (holder is BookViewHolderWide) {
            Glide.with(holder.itemView.context).clear(holder.binding.bookImage)
        }
        if (holder is BookViewHolderSlim) {
            Glide.with(holder.itemView.context).clear(holder.binding.bookImage)
        }
        super.onViewRecycled(holder)
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
        return when (viewType) {
            BookView.BookDisplayStyle.WIDE.id-> {
                val binding = ViewBookWideBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                BookViewHolderWide(binding)
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
                val newFavoriteState = !book.isFavorite
                onFavoriteClick?.invoke(book, newFavoriteState)
            }

            holder.updateFavoriteState(book.isFavorite)
            holder.setButtonClickListener {
                onBookClick?.invoke(book)
            }
        }
    }


    override fun getItemCount(): Int = books.size

    class BookViewHolderWide(internal val binding: ViewBookWideBinding) :
        RecyclerView.ViewHolder(binding.root),
        BookViewHolder {

        override fun bind(book: Book) {
            val context = binding.root.context
            binding.bookTitleText.text = book.title
            binding.authorText.text = book.author
            binding.bookReadPercent.text = "${book.percentRead?:0}%"

            loadDrawableFromUrl(
                context = context,
                url = book.coverURL.toString(),
                onSuccess = { drawable ->
                    binding.bookImage.setImageDrawable(drawable)
                },
                onError = { exception ->
                    exception.printStackTrace()
                }
            )
        }

        override fun setFavoriteClickListener(action: () -> Unit) {
            binding.favoriteButton.setOnClickListener { action() }
        }

        override fun updateFavoriteState(isFavorite: Boolean) {
            binding.favoriteButton.isActivated = isFavorite
        }

        override fun setButtonClickListener(action: () -> Unit) {
            binding.bookImage.setOnClickListener {
                action()
            }
        }
    }


    class BookViewHolderSlim(internal val binding: ViewBookSlimBinding) :
        RecyclerView.ViewHolder(binding.root),
        BookViewHolder  {

        override fun bind(book: Book) {
            val context = binding.root.context
            binding.bookTitleText.text = book.title
            binding.bookReadPercent.text = "${book.percentRead?:0}%"
            loadDrawableFromUrl(
                context = context,
                url = book.coverURL.toString(),
                onSuccess = { drawable ->
                    binding.bookImage.setImageDrawable(drawable)
                },
                onError = { exception ->
                    exception.printStackTrace()
                }
            )
        }

        override fun setFavoriteClickListener(action: () -> Unit) {
            binding.favoriteButton.setOnClickListener { action() }
        }

        override fun updateFavoriteState(isFavorite: Boolean) {
            binding.favoriteButton.isActivated = isFavorite
        }

        override fun setButtonClickListener(action: () -> Unit) {
            binding.bookImage.setOnClickListener{
                action()
            }
        }
    }

    companion object{
        fun loadDrawableFromUrl(
            context: Context,
            url: String,
            onSuccess: (Drawable) -> Unit,
            onError: (Exception) -> Unit = {}
        ) {
            Glide.with(context)
                .load(url)
                .placeholder(R.color.gray_blue_700)
                .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        onSuccess(resource)
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        onError(Exception("Failed to load image from $url"))
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // TODO()
                    }
                })
        }
    }

    class BookDiffCallback(
        private val oldList: List<Book>,
        private val newList: List<Book>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id // Сравните по уникальному идентификатору
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }


}
