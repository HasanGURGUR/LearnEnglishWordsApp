package com.learn.vocabulary.ui.learn.word.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.learn.vocabulary.base.BaseViewHolder
import com.learn.vocabulary.databinding.WordDetailLayoutBinding
import com.learn.vocabulary.model.Word

import com.learn.vocabulary.model.WordDetailModel

class WordDetailItemHolder(
    parent: ViewGroup,
    inflater: LayoutInflater
) : BaseViewHolder<WordDetailLayoutBinding>(
    binding = WordDetailLayoutBinding.inflate(inflater, parent, false)
) {

    fun bind(
        word: Word,
        characterClickCallback: ((Word) -> Unit)? = null,
        favClickCallback: ((Word) -> Unit)? = null
    ) {
        with(binding) {
            binding.wordDetail = word
            binding.mainLayout.setOnClickListener {
                characterClickCallback?.invoke(word)
            }

            binding.favIcon.setOnClickListener {
                favClickCallback?.invoke(word)
            }
            executePendingBindings()
        }
    }


}