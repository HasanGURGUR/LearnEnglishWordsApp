package com.learn.vocabulary.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.learn.vocabulary.util.Constant.USER_TABLE



@Entity(tableName = USER_TABLE)
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val userr: Int,
    @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "user_photo") val userPhoto: ByteArray?
)
