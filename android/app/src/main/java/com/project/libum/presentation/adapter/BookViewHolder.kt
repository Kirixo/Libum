package com.project.libum.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.project.libum.data.dto.Book

interface BookViewHolder{
    fun bind(book: Book)
}