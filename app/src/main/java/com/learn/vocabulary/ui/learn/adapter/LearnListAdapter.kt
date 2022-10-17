package com.learn.vocabulary.ui.learn.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learn.vocabulary.base.BaseListAdapter
import com.learn.vocabulary.model.Cat
import com.learn.vocabulary.model.WordModel

class LearnListAdapter (
    private val characterClickCallback: ((Cat?) -> Unit)?
) : BaseListAdapter<Cat>(
    itemsSame = { old, new -> old.id == new.id },
    contentsSame = { old, new -> old == new }
) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LearnItemHolder -> {
                holder.bind(
                    vocabulary = getItem(position),
                    characterClickCallback = characterClickCallback
                )
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return LearnItemHolder(parent, inflater)
    }

}