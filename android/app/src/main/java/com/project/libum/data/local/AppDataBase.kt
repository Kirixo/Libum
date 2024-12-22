package com.project.libum.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.libum.data.local.dao.BookDao
import com.project.libum.data.local.dao.BookEntity
import com.project.libum.data.local.dao.UserDao
import com.project.libum.data.local.dao.UserEntity

@Database(entities = [UserEntity::class, BookEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun bookDao(): BookDao
}
