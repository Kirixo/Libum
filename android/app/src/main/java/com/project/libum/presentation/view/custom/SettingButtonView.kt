package com.project.libum.presentation.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
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
    private val root: RelativeLayout
    private val additionalText: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.view_setting_custom_button, this, true)

        logoImageView = findViewById(R.id.button_logo)
        textView = findViewById(R.id.button_text)
        actionImageButton = findViewById(R.id.button_arrow)
        root = findViewById(R.id.all_size_button)
        additionalText = findViewById(R.id.additional_text_field)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.SettingButtonView, 0, 0)
            try {
                val logoResId = typedArray.getResourceId(R.styleable.SettingButtonView_button_logo, -1)
                setLogoResource(logoResId)

                val text = typedArray.getString(R.styleable.SettingButtonView_button_text)
                setText(text)

                val actionButtonResId = typedArray.getResourceId(R.styleable.SettingButtonView_action_button, 0)

                if (actionButtonResId != 0){
                    setArrowVisibility(actionButtonResId)
                }else{
                    actionImageButton.visibility = GONE
                }

                val additionalText =  typedArray.getString(R.styleable.SettingButtonView_additional_text)
                setAdditionalText(additionalText)

            } finally {
                typedArray.recycle()
            }
        }
    }

    fun setLogoResource(resId: Int?) {
        if (resId == null || resId == -1){
            logoImageView.visibility = GONE
        }else{
            logoImageView.visibility = VISIBLE
            logoImageView.setImageResource(resId)
        }
    }

    fun setText(text: String?) {
        textView.text = text
    }

    fun setAdditionalText(text: String?){
        additionalText.text = text
    }

    fun setArrowVisibility(resId: Int) {
        actionImageButton.setImageResource(resId)
    }

    fun setOnActionButtonClickListener(listener: (View) -> Unit) {
        actionImageButton.setOnClickListener {
            listener(it)
        }
    }

    fun setOnButtonClickListener(listener: () -> Unit) {
        root.setOnClickListener {
            listener()
        }
    }
}