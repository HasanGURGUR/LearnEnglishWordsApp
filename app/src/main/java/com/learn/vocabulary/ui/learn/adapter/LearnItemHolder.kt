package com.learn.vocabulary.ui.learn.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.learn.vocabulary.base.BaseViewHolder
import com.learn.vocabulary.databinding.LearnItemLayoutBinding
import com.learn.vocabulary.model.Cat


class LearnItemHolder (
    parent: ViewGroup,
    inflater: LayoutInflater
) : BaseViewHolder<LearnItemLayoutBinding>(
    binding = LearnItemLayoutBinding.inflate(inflater, parent, false)
) {

    fun bind(
        vocabulary : Cat,
        characterClickCallback : ((Cat) -> Unit)? = null
    ) {
        with(binding) {
            binding.vocabulary = vocabulary
            binding.mainLayout.setOnClickListener{
                characterClickCallback?.invoke(vocabulary)
            }
            executePendingBindings()
        }
    }


}
