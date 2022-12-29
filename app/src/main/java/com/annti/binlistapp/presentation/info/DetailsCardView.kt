package com.annti.binlistapp.presentation.info

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.annti.binlistapp.databinding.ViewDetailsCardBinding

class DetailsCardView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = ViewDetailsCardBinding.inflate(LayoutInflater.from(context))

    init {
        binding.root.layoutParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        addView(binding.root)
    }

    fun setTextName(text: String){
        binding.textName.text = text
    }
    fun setTextValue(text: String){
        binding.textValue.text = text
    }
    fun navigateToNext(): ImageButton {
        return binding.buttonNext
    }
}