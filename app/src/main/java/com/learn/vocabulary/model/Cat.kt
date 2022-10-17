package com.learn.vocabulary.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cat(
    val id: Int,
    val image: String,
    val name: String,
    val count: String
) : Parcelable