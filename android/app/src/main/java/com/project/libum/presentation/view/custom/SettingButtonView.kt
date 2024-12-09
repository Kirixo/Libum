package com.project.libum.presentation.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.project.libum.R

class SettingButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val logoImageView: ImageView
    private val textView: TextView
    private val actionImageButton: ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.view_setting_custom_button, this, true)

        logoImageView = findViewById(R.id.button_logo)
        textView = findViewById(R.id.button_text)
        actionImageButton = findViewById(R.id.button_arrow)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.SettingButtonView, 0, 0)
            try {
                val logoResId = typedArray.getResourceId(R.styleable.SettingButtonView_button_logo, 0)
                if (logoResId != 0) setLogoResource(logoResId)

                val text = typedArray.getString(R.styleable.SettingButtonView_button_text)
                setText(text)

                val actionButtonResId = typedArray.getResourceId(R.styleable.SettingButtonView_action_button, 0)

                if (actionButtonResId != 0){
                    setArrowVisibility(actionButtonResId)
                }else{
                    setArrowVisibility(R.drawable.ic_action_arrow)
                }

            } finally {
                typedArray.recycle()
            }
        }
    }

    fun setLogoResource(resId: Int) {
        logoImageView.setImageResource(resId)
    }

    fun setText(text: String?) {
        textView.text = text
    }

    fun setArrowVisibility(resId: Int) {
        actionImageButton.setImageResource(resId)
    }

    fun setOnActionButtonClickListener(listener: () -> Unit) {
        actionImageButton.setOnClickListener {
            listener()
        }
    }

    fun setOnButtonClickListener(listener: () -> Unit) {
        actionImageButton.setOnClickListener {
            listener()
        }
    }
}