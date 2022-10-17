package com.learn.vocabulary.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.learn.vocabulary.model.Word

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}