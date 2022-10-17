package com.learn.vocabulary.data.local

import androidx.room.*
import com.learn.vocabulary.model.Word
import com.learn.vocabulary.model.WordDetailModel
import com.learn.vocabulary.util.Constant.USER_TABLE

@Dao
interface UserDao {
    @Query("SELECT * FROM $USER_TABLE WHERE userr LIKE :id")
    fun getUser(id: Int): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhoto(taskEntity: UserEntity)

    @Update
    fun updateTask(taskEntity: UserEntity)
}