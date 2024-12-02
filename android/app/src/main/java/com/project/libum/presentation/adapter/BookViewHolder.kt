package com.project.libum.presentation.adapter

import com.project.libum.data.dto.Book

interface BookViewHolder{
    fun bind(book: Book)
    fun setFavoriteClickListener(action: () -> Unit)
    fun updateFavoriteState(isFavorite: Boolean)
    fun setButtonClickListener(action: () -> Unit)
}