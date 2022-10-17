package com.learn.vocabulary.ui.learn.word.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learn.vocabulary.base.BaseListAdapter
import com.learn.vocabulary.model.Word


class WordDetailItemAdapter(
    private val characterClickCallback: ((Word?) -> Unit)?,
    private val favClick: ((Word?) -> Unit)?
) : BaseListAdapter<Word>(
    itemsSame = { old, new -> old.id == new.id },
    contentsSame = { old, new -> old == new }
) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WordDetailItemHolder -> {
                holder.bind(
                    word = getItem(position),
                    characterClickCallback = characterClickCallback,
                    favClick
                )
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return WordDetailItemHolder(parent, inflater)
    }

}