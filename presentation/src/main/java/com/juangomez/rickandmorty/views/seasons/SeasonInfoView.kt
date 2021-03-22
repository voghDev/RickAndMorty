package com.juangomez.rickandmorty.views.seasons

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.juangomez.rickandmorty.R
import com.juangomez.rickandmorty.databinding.SeasonInfoViewBinding

class SeasonInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: SeasonInfoViewBinding by lazy {
        SeasonInfoViewBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setup(text: String) {
        binding.coverImage.setImageResource(R.drawable.cover)
        binding.summaryText.text = text
    }
}