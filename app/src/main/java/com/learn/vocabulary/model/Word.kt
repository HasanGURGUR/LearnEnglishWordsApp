package com.learn.vocabulary.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.learn.vocabulary.util.Constant

@Entity(tableName = Constant.USER_TABLE)
data class Word(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "cat_id") val cat_id: String,
    @ColumnInfo(name = "example") val example: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "meaning_tr") val meaning_tr: String,
    @ColumnInfo(name = "word") val word: String,
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean = false
)