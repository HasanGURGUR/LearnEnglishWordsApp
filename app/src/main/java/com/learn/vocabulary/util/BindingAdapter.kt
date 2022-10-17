package com.learn.vocabulary.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.learn.vocabulary.R

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadImageFromUrl(imageview: ImageView, url: String?) {
        if (url.isNullOrEmpty()) {
            Glide.with(imageview.context).load(R.drawable.ic_baseline_hide_image_24).into(imageview)
        } else {
            Glide.with(imageview.context).load(url).into(imageview)
        }
    }

    @JvmStatic
    @BindingAdapter("capitalizeString")
    fun capitalizeString(textView: TextView, str: String) {

        try {
            textView.text = str.first().uppercaseChar() + str.substring(1)
        } catch (e: Exception) {
        }

    }
}