package com.project.libum.presentation.adapter

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SpacingItemDecoration(
    private val space: Int,
    private val firstTopSpacing: Int,
    private val lastBottomSpacing: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val spanCount = 1
        val itemCount = state.itemCount

        outRect.bottom = space

        if (position < spanCount) {
            outRect.top = firstTopSpacing
        }

        if (position == itemCount - 1) {
            outRect.bottom = lastBottomSpacing
        }
    }
}

class GridSpacingItemDecoration(
    private val topSpacing: Int,
    private val firstTopSpacing: Int,
    private val lastBottomSpacing: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val spanCount = (parent.layoutManager as? GridLayoutManager)?.spanCount ?: 1
        val itemCount = state.itemCount


        outRect.bottom = topSpacing

        if (position < spanCount) {
            outRect.top = firstTopSpacing
        }

        if (position > itemCount+1 - spanCount) {
            outRect.bottom = lastBottomSpacing
        }
    }
}
