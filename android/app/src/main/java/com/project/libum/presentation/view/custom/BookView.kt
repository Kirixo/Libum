package com.project.libum.presentation.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.project.libum.R

class BookView: ConstraintLayout {

    private var displayMode: BookDisplayStyle = BookDisplayStyle.WIDE
    private lateinit var authorView: TextView
    private lateinit var bookTitleView: TextView
    private lateinit var byPremiumView: ImageView
    private lateinit var readPercentView: TextView
    private lateinit var moreButton: ImageButton
    private lateinit var favoriteButton: ImageButton

    constructor(context: Context): super(context){
        initializeView()
    }
    constructor(context: Context, attrs: AttributeSet?): super(context, attrs){
        initializeView(context, attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr){
        initializeView(context, attrs)
    }


    private fun initializeView(context: Context, attrs: AttributeSet?){
        context.theme.obtainStyledAttributes(attrs, R.styleable.BookView, 0, 0).apply {
            try {
                val mode = getInt(R.styleable.BookView_displayMode, 0)
                displayMode = if (mode == 0) BookDisplayStyle.WIDE else BookDisplayStyle.SLIM
            } finally {
                recycle()
            }
        }
        initializeView()
    }

    private fun initializeView(){
        updateLayout()
        initializeBookInfoFields()
    }

    private fun updateLayout() {
        removeAllViews()
        val layoutRes = when (displayMode) {
            BookDisplayStyle.WIDE -> R.layout.view_book_wide
            BookDisplayStyle.SLIM -> R.layout.view_book_slim
        }
        LayoutInflater.from(context).inflate(layoutRes, this, true)
    }

    private fun initializeBookInfoFields(){

        if(displayMode == BookDisplayStyle.WIDE){
            authorView = findViewById(R.id.author_text)
        }

        bookTitleView = findViewById(R.id.book_title_text)
        byPremiumView = findViewById(R.id.premium_icon)
        readPercentView = findViewById(R.id.book_read_percent)
        moreButton = findViewById(R.id.more_options_button)
        favoriteButton = findViewById(R.id.favorite_button)

    }

    enum class BookDisplayStyle(val id: Int) {
        WIDE(0),
        SLIM(1),
    }
}